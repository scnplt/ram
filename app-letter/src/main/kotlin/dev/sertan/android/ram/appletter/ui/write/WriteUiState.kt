/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appletter.ui.write

internal data class WriteUiState(
    val letter: String,
    val isNextButtonVisible: Boolean,
    val isFinishButtonVisible: Boolean
) {

    companion object {
        fun initialState(): WriteUiState = WriteUiState(
            letter = "a",
            isNextButtonVisible = false,
            isFinishButtonVisible = false
        )
    }
}
