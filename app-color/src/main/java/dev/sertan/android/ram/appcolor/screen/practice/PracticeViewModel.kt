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

    private var isValidationActive = true
    private val questionIndex = MutableStateFlow(0)

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

    fun setAnswerValidation(isActive: Boolean) {
        isValidationActive = isActive
    }

    fun isMaterialCorrect(material: Material): Boolean? =
        if (isValidationActive) uiState.value.question?.correctMaterialId == material.uid else null
}
