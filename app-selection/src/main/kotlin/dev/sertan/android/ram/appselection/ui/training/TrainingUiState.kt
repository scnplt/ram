/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.ui.training

import dev.sertan.android.ram.core.common.percent
import dev.sertan.android.ram.core.model.ui.Material
import kotlin.math.roundToInt

internal data class TrainingUiState(
    val material: Material?,
    val isBackButtonVisible: Boolean,
    val isForwardButtonVisible: Boolean,
    val isFinishButtonVisible: Boolean,
    val progress: Int,
    val isEmptyListMessageVisible: Boolean?
) {

    companion object {

        fun initialState(): TrainingUiState = TrainingUiState(
            material = null,
            isBackButtonVisible = false,
            isForwardButtonVisible = false,
            isFinishButtonVisible = false,
            progress = 0,
            isEmptyListMessageVisible = null
        )

        fun getState(materials: List<Material>, index: Int): TrainingUiState = TrainingUiState(
            material = materials.getOrNull(index),
            isBackButtonVisible = index > 0,
            isForwardButtonVisible = index in 0 until materials.lastIndex,
            isFinishButtonVisible = index == materials.lastIndex,
            progress = percent(
                value = index.inc().toFloat(),
                total = materials.size.toFloat()
            ).roundToInt(),
            isEmptyListMessageVisible = materials.isEmpty()
        )
    }
}
