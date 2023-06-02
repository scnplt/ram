/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.matching

import dev.sertan.android.ram.appmemory.ui.matching.adapter.MatchingListItem

internal data class MatchingUiState(
    val contentItems: List<MatchingListItem>,
    val isNextButtonInvisible: Boolean,
    val isFinishButtonVisible: Boolean,
    val isEmptyListMessageVisible: Boolean?
) {

    companion object {
        fun initialState(): MatchingUiState = MatchingUiState(
            contentItems = emptyList(),
            isNextButtonInvisible = true,
            isFinishButtonVisible = false,
            isEmptyListMessageVisible = null
        )
    }
}
