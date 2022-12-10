/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.ui.practice

import dev.sertan.android.ram.core.model.ui.Question

internal data class PracticeUiState(
    val question: Question?,
    val isForwardButtonVisible: Boolean,
    val isFinishButtonVisible: Boolean
) {

    companion object {

        fun initialState(): PracticeUiState = PracticeUiState(
            question = null,
            isForwardButtonVisible = false,
            isFinishButtonVisible = false
        )

        fun getState(questions: List<Question>, index: Int): PracticeUiState =
            PracticeUiState(
                question = if (questions.isNotEmpty()) questions[index] else null,
                isForwardButtonVisible = index in 0 until questions.lastIndex,
                isFinishButtonVisible = index == questions.lastIndex
            )
    }
}
