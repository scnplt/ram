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
import androidx.annotation.ColorInt
import dev.sertan.android.ram.core.common.Event

internal data class AudioInstructionUiState(
    val isCatchButtonEnabled: Boolean,
    val isStartButtonInvisible: Boolean,
    val isRingtonePlaying: Event<Boolean>,
    val isFinished: Boolean,
    @ColorInt val catchButtonTint: Int
) {

    companion object {

        fun getInitialState(): AudioInstructionUiState = AudioInstructionUiState(
            isCatchButtonEnabled = false,
            isStartButtonInvisible = false,
            isRingtonePlaying = Event(false),
            isFinished = false,
            catchButtonTint = Color.BLACK
        )

        fun getFinishState(): AudioInstructionUiState = AudioInstructionUiState(
            isCatchButtonEnabled = false,
            isStartButtonInvisible = false,
            isRingtonePlaying = Event(false),
            isFinished = true,
            catchButtonTint = Color.BLACK
        )
    }
}
