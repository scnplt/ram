/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.ui.counting

internal sealed class CountingUiState {

    object Idle : CountingUiState()

    object Failure : CountingUiState()

    data class Success(
        val number: Int,
        val step: Int,
        val isNextSectionButtonVisible: Boolean,
        val isFinishButtonVisible: Boolean,
        val isMicButtonEnabled: Boolean
    ) : CountingUiState()
}
