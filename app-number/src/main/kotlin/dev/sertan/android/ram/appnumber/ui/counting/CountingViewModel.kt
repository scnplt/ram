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
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.appnumber.domain.usecase.GetSectionsUseCase
import dev.sertan.android.ram.core.domain.usecase.SpeechToTextUseCase
import dev.sertan.android.ram.core.domain.usecase.SpeechToTextUseCase.Companion.CONVERT_FAIL
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class CountingViewModel @Inject constructor(
    private val getSectionsUseCase: GetSectionsUseCase,
    private val speechToTextUseCase: SpeechToTextUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CountingUiState.initialState())
    val uiState = _uiState.asStateFlow()

    fun listenToNumber(): Unit = speechToTextUseCase(
        onComplete = { input ->
            val number = speechToTextUseCase.convertWordToNumber(text = input)
                .takeUnless { it == CONVERT_FAIL } ?: return@speechToTextUseCase
            checkNextNumberAndUpdateState(number)
        },
        onError = { errorCode -> _uiState.update { it.copy(errorCode = errorCode) } }
    )

    private fun checkNextNumberAndUpdateState(number: Int) {
        _uiState.update { state -> state.copy(isCorrect = state.nextNumber == number) }
    }
}
