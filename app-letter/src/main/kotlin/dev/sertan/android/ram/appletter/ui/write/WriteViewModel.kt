/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appletter.ui.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.core.domain.usecase.GetApplicationConfigsUseCase
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val LETTERS_KEY = "letters"
private const val LETTER_DELIMITER = ','
private const val NEXT_QUESTION_DELAY_MS = 750L

@HiltViewModel
internal class WriteViewModel @Inject constructor(
    getApplicationConfigsUseCase: GetApplicationConfigsUseCase,
    private val textToSpeechUseCase: TextToSpeechUseCase
) : ViewModel() {

    private val letterIndex = MutableStateFlow(0)
    private val letters = getApplicationConfigsUseCase(key = LETTERS_KEY).split(LETTER_DELIMITER)

    var listener: AnswerListener? = null

    val uiState: StateFlow<WriteUiState> = letterIndex.map { index ->
        val nextLetter = letters.getOrNull(index) ?: "a"
        textToSpeechUseCase.speak(message = nextLetter)
        WriteUiState(
            letter = nextLetter,
            isNextButtonVisible = index < letters.lastIndex,
            isFinishButtonVisible = index == letters.lastIndex
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = WriteUiState.initialState()
    )

    fun nextLetterIfAnswerCorrect(input: String) {
        viewModelScope.launch {
            if (!checkInput(input)) return@launch
            delay(NEXT_QUESTION_DELAY_MS)
            letterIndex.update { it.inc() }
        }

    }

    fun speakCurrentLetter(): Unit = textToSpeechUseCase.speak(message = uiState.value.letter)

    fun checkInput(input: String): Boolean {
        val isCorrect = uiState.value.letter == input.trim().lowercase()
        if (isCorrect) listener?.onCorrect() else listener?.onWrong()
        return isCorrect
    }

    interface AnswerListener {
        fun onCorrect()
        fun onWrong()
    }
}
