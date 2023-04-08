/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.posedetection

import dev.sertan.android.ram.feature.posedetection.domain.motion.Motion

internal data class PoseDetectionUiState(
    val motion: Motion?,
    val isNextButtonVisible: Boolean,
    val isFinishButtonVisible: Boolean
) {

    companion object {

        fun initialState(): PoseDetectionUiState = PoseDetectionUiState(
            motion = null,
            isNextButtonVisible = false,
            isFinishButtonVisible = false
        )

        fun getState(index: Int, motions: List<Motion>): PoseDetectionUiState {
            return PoseDetectionUiState(
                motion = motions.getOrNull(index),
                isNextButtonVisible = index < motions.lastIndex,
                isFinishButtonVisible = index == motions.lastIndex
            )
        }
    }
}
