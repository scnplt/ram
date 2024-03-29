/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.gapfilling

import dev.sertan.android.ram.appmemory.ui.model.GapFillingQuestion

internal data class GapFillingUiState(
    val gapFillingQuestion: GapFillingQuestion?,
    val isForwardButtonVisible: Boolean,
    val isFinishButtonVisible: Boolean,
    val isEmptyListMessageVisible: Boolean?
) {

    companion object {
        fun initialState(): GapFillingUiState = GapFillingUiState(
            gapFillingQuestion = null,
            isForwardButtonVisible = false,
            isFinishButtonVisible = false,
            isEmptyListMessageVisible = null
        )
    }
}
