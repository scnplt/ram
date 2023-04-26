/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.voiceimitation

internal data class VoiceImitationUiState(
    val sound: String?,
    val isForwardButtonVisible: Boolean,
    val isFinishButtonVisible: Boolean,
    val isBackButtonInvisible: Boolean
) {

    companion object {

        fun initialState(): VoiceImitationUiState = VoiceImitationUiState(
            sound = null,
            isForwardButtonVisible = false,
            isFinishButtonVisible = false,
            isBackButtonInvisible = true
        )
    }
}
