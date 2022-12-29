/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.ui.counting

internal data class CountingUiState(
    val nextNumber: Int,
    val isCorrect: Boolean?,
    val errorCode: Int?
) {

    companion object {
        fun initialState(): CountingUiState = CountingUiState(
            nextNumber = 0,
            isCorrect = null,
            errorCode = null
        )
    }
}
