/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.ui.counting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.appnumber.domain.usecase.GetSectionsUseCase
import dev.sertan.android.ram.appnumber.ui.model.Section
import dev.sertan.android.ram.core.domain.usecase.SpeechToTextUseCase
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class CountingViewModel @Inject constructor(
    getSectionsUseCase: GetSectionsUseCase,
    private val speechToTextUseCase: SpeechToTextUseCase,
    private val textToSpeechUseCase: TextToSpeechUseCase
) : ViewModel() {

    private val sectionIndexStream = MutableStateFlow(0)
    private val numberIndexStream = MutableStateFlow(0)

    var listener: NumberListener? = null

    private val sectionsStream = getSectionsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    private var currentSection: StateFlow<Section?> =
        combine(sectionsStream, sectionIndexStream) { sections, sectionIndex ->
            sections.getOrNull(sectionIndex)?.also { numberIndexStream.update { 0 } }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    private val _uiState = MutableStateFlow<CountingUiState>(CountingUiState.Idle)
    val uiState get() = _uiState.asStateFlow()

    init {
        combine(currentSection, numberIndexStream) { section, numberIndex ->
            if (section == null) return@combine CountingUiState.Failure
            with(section) {
                val index = sectionIndexStream.value
                val lastSectionIndex = sectionsStream.value.lastIndex
                val number = numberIndex * step + minNumber
                val state = CountingUiState.Success(
                    number = number,
                    step = step,
                    isNextSectionButtonVisible = number == maxNumber && index < lastSectionIndex,
                    isFinishButtonVisible = number == maxNumber && index == lastSectionIndex,
                    isMicButtonEnabled = number + step <= maxNumber
                )
                _uiState.update { state }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = CountingUiState.Idle
        )
    }

    fun listenToNumber() {
        listener?.onStart()
        speechToTextUseCase(
            onComplete = { input ->
                val section = currentSection.value ?: return@speechToTextUseCase
                val numberInput = speechToTextUseCase.convertWordToNumber(text = input)
                val nextNumber =
                    numberIndexStream.value * section.step + section.minNumber + section.step
                listener?.onComplete()
                if (numberInput != nextNumber) {
                    listener?.onWrong()
                    return@speechToTextUseCase
                }
                listener?.onCorrect(nextNumber, section.step)
                numberIndexStream.update { it.inc() }
            },
            onError = { errorCode -> listener?.onError(errorCode) }
        )
    }

    fun speak(text: String): Unit = textToSpeechUseCase.speak(message = text)

    fun stopSpeech(): Unit = textToSpeechUseCase.stopSpeech()

    fun nextSection(): Unit = sectionIndexStream.update { it.inc() }

    interface NumberListener {
        fun onStart()
        fun onCorrect(newNumber: Int, step: Int)
        fun onWrong()
        fun onComplete()
        fun onError(errorCode: Int)
    }
}
