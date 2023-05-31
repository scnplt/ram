/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.matching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.appmemory.domain.usecase.MatchingQuestionsUseCase
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class MatchingViewModel @Inject constructor(
    getMatchingQuestionsUseCase: MatchingQuestionsUseCase,
    private val textToSpeechUseCase: TextToSpeechUseCase
) : ViewModel() {

    var isValidationActive = true
    private val questionIndex = MutableStateFlow(0)

    val uiState: StateFlow<MatchingUiState> = flow {
        val questions = getMatchingQuestionsUseCase()
        questionIndex.collect { index ->
            val question = questions.getOrNull(index)
            emit(
                MatchingUiState(
                    matchingQuestion = question,
                    isForwardButtonVisible = index in 0 until questions.lastIndex,
                    isFinishButtonVisible = index == questions.lastIndex,
                    isEmptyListMessageVisible = questions.isEmpty()
                )
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = MatchingUiState.initialState()
    )

    fun goToNextQuestion() {
        questionIndex.update { it.inc() }
        isValidationActive = true
    }

    fun speakCurrentQuestionContent(): Unit =
        textToSpeechUseCase.speak(uiState.value.matchingQuestion?.content)

    fun stopSpeech(): Unit = textToSpeechUseCase.stopSpeech()
}
