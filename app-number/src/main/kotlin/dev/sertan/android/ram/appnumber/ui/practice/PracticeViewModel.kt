/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.ui.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.appnumber.domain.usecase.GetQuestionsUseCase
import dev.sertan.android.ram.appnumber.ui.model.Material
import dev.sertan.android.ram.core.common.percent
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class PracticeViewModel @Inject constructor(
    getQuestionsUseCase: GetQuestionsUseCase,
    private val textToSpeechUseCase: TextToSpeechUseCase
) : ViewModel() {

    var isValidationActive = true
    private val questionIndex = MutableStateFlow(0)
    private var correctAnswerCount = 0f
    private var wrongAnswerCount = 0f

    val score: Float
        get() = percent(value = correctAnswerCount, total = correctAnswerCount + wrongAnswerCount)

    val uiState: StateFlow<PracticeUiState> = flow {
        val questions = getQuestionsUseCase()
        questionIndex.collect { index ->
            textToSpeechUseCase.checkStateAndSpeak(questions.getOrNull(index)?.content)
            emit(PracticeUiState.getState(questions, index))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PracticeUiState.initialState()
    )

    fun goToNextQuestion(): Unit = questionIndex.update { it.inc() }

    fun speakCurrentQuestionContent(): Unit =
        textToSpeechUseCase.speak(uiState.value.question?.content)

    fun isMaterialCorrect(material: Material): Boolean? = uiState.takeIf { isValidationActive }
        ?.value?.question?.correctMaterialUid?.equals(material.uid)

    fun setAnswerState(isCorrect: Boolean) {
        if (isCorrect) correctAnswerCount++ else wrongAnswerCount++
    }

    fun stopSpeech(): Unit = textToSpeechUseCase.stopSpeech()
}
