/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.visualinstruction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.appcommunication.ui.audioinstruction.MS_PER_S
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val MIN_DELAY_S = 1
private const val MAX_DELAY_S = 4
private const val COUNT = 10
private const val CLICK_DELAY_MS = 500L

@HiltViewModel
internal class VisualInstructionViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(VisualInstructionUiState.getInitialState())
    val uiState: StateFlow<VisualInstructionUiState> = _uiState.asStateFlow()

    private var catchJob: Job? = null

    fun startGame() {
        _uiState.update {
            it.copy(
                isCatchButtonInvisible = true,
                isStartButtonInvisible = true
            )
        }
        viewModelScope.launch {
            repeat(COUNT) {
                _uiState.update { it.copy(isCatchButtonInvisible = true) }
                val randomDelay = (MIN_DELAY_S..MAX_DELAY_S).random() * MS_PER_S
                delay(randomDelay)
                _uiState.update { it.copy(isCatchButtonInvisible = false) }
                delay(CLICK_DELAY_MS)
            }

            delay(CLICK_DELAY_MS)
            catchJob?.cancel()
            _uiState.update { VisualInstructionUiState.getFinishState() }
        }
    }

    fun catch() {
        _uiState.update { it.copy(isCatchButtonInvisible = true) }
    }
}
