/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.corecommon.util.percent
import dev.sertan.android.ram.coredomain.usecase.GetQuestionsUseCase
import dev.sertan.android.ram.coredomain.usecase.VoiceSupportUseCase
import dev.sertan.android.ram.coreui.model.Material
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class PracticeViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val voiceSupportUseCase: VoiceSupportUseCase
) : ViewModel() {

    var isValidationActive = true
    private val questionIndex = MutableStateFlow(0)

    private var correctAnswerCount = 0f
    private var wrongAnswerCount = 0f

    val score: Float
        get() = percent(value = correctAnswerCount, total = correctAnswerCount + wrongAnswerCount)

    val uiState: StateFlow<PracticeUiState> = flow {
        val questions = getQuestionsUseCase()
        questionIndex.collect { emit(PracticeUiState.getState(questions = questions, index = it)) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PracticeUiState.initialState()
    )

    init {
        viewModelScope.launch {
            uiState.collect {
                voiceSupportUseCase.checkVoiceSupportStateAndSpeak(it.question?.content)
            }
        }
    }

    fun goToNextQuestion(): Unit = questionIndex.update { it.inc() }

    fun speakCurrentQuestionContent(): Unit =
        voiceSupportUseCase.speak(uiState.value.question?.content)

    fun stopSpeech(): Unit = voiceSupportUseCase.stopSpeech()

    fun isMaterialCorrect(material: Material): Boolean? =
        uiState.value.question?.correctMaterialId.equals(material.uid).takeIf { isValidationActive }

    fun setAnswerState(isCorrect: Boolean) {
        if (isCorrect) correctAnswerCount++ else wrongAnswerCount++
    }
}
