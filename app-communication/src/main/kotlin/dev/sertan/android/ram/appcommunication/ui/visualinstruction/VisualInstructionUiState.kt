/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.visualinstruction

internal data class VisualInstructionUiState(
    val isCatchButtonInvisible: Boolean,
    val isStartButtonInvisible: Boolean,
    val isFinished: Boolean
) {

    companion object {

        fun getInitialState(): VisualInstructionUiState = VisualInstructionUiState(
            isCatchButtonInvisible = false,
            isStartButtonInvisible = false,
            isFinished = false
        )

        fun getFinishState(): VisualInstructionUiState = VisualInstructionUiState(
            isCatchButtonInvisible = false,
            isStartButtonInvisible = false,
            isFinished = true
        )
    }
}
