/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.bodyparts

import androidx.annotation.DrawableRes

internal data class BodyUiState(
    @DrawableRes val bodyImageResId: Int?,
    val part: BodyPart?,
    val isForwardButtonInvisible: Boolean,
    val isFinishButtonVisible: Boolean
) {

    companion object {

        fun initialState(): BodyUiState = BodyUiState(
            bodyImageResId = null,
            part = null,
            isForwardButtonInvisible = true,
            isFinishButtonVisible = false
        )
    }
}
