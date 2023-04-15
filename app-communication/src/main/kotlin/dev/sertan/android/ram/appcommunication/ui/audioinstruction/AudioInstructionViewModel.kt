/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.audioinstruction

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.core.common.Event
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal const val MS_PER_S = 1000L
private const val MIN_DELAY_S = 2
private const val MAX_DELAY_S = 5
private const val COUNT = 10
private const val CLICK_DELAY_MS = 1500L
private const val RESET_TINT_DELAY_MS = 750L

@HiltViewModel
internal class AudioInstructionViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AudioInstructionUiState.getInitialState())
    val uiState: StateFlow<AudioInstructionUiState> = _uiState.asStateFlow()

    private var lastPlayedTimeMs = System.currentTimeMillis()
    private var catchJob: Job? = null

    fun startGame() {
        _uiState.update { it.copy(isCatchButtonEnabled = true, isStartButtonInvisible = true) }
        viewModelScope.launch {
            repeat(COUNT) {
                delay((MIN_DELAY_S..MAX_DELAY_S).random() * MS_PER_S)
                _uiState.update {
                    lastPlayedTimeMs = System.currentTimeMillis()
                    it.copy(isRingtonePlaying = Event(true))
                }
            }

            delay(CLICK_DELAY_MS + RESET_TINT_DELAY_MS)
            catchJob?.cancel()
            _uiState.update { AudioInstructionUiState.getFinishState() }
        }
    }

    fun catch() {
        catchJob?.cancel()
        catchJob = viewModelScope.launch {
            val result = System.currentTimeMillis() - lastPlayedTimeMs < CLICK_DELAY_MS
            _uiState.update { it.copy(catchButtonTint = if (result) Color.GREEN else Color.RED) }

            delay(RESET_TINT_DELAY_MS)
            _uiState.update { it.copy(catchButtonTint = Color.BLACK) }
        }
    }
}
