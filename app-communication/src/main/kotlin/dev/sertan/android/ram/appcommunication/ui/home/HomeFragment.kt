/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.home

import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcommunication.R
import dev.sertan.android.ram.appcommunication.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToAudioInstructionFragment
import dev.sertan.android.ram.appcommunication.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToBodyPartsFragment
import dev.sertan.android.ram.appcommunication.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToDrawingFragment
import dev.sertan.android.ram.appcommunication.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToObjectRecognitionFragment
import dev.sertan.android.ram.appcommunication.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToVisualInstructionFragment
import dev.sertan.android.ram.appcommunication.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToVoiceImitationFragment
import dev.sertan.android.ram.core.ui.util.extension.doIfPermissionGranted
import dev.sertan.android.ram.core.ui.util.extension.labelWithoutPrefix
import dev.sertan.android.ram.core.ui.util.extension.navTo
import dev.sertan.android.ram.core.ui.util.extension.resultLauncher
import dev.sertan.android.ram.core.ui.util.extension.showToast
import dev.sertan.android.ram.feature.home.BaseHomeFragment
import dev.sertan.android.ram.feature.home.adapter.HomeListItem

@AndroidEntryPoint
internal class HomeFragment : BaseHomeFragment() {

    private val micResultLauncher = resultLauncher(
        onDenied = { showToast(dev.sertan.android.ram.core.ui.R.string.mic_permission_denied) }
    )

    override val items: List<HomeListItem>
        get() = listOf(
            HomeListItem.TitleItem(title = requireContext().labelWithoutPrefix),
            HomeListItem.HeaderItem(iconResId = R.drawable.ic_splash),
            HomeListItem.ButtonItem(
                buttonTextResId = R.string.attention_practice,
                buttonIconResId = R.drawable.ic_audiotrack,
                onClicked = { navTo(actionHomeFragmentToAudioInstructionFragment()) }
            ),
            HomeListItem.ButtonItem(
                buttonTextResId = R.string.attention_practice,
                buttonIconResId = R.drawable.ic_eye,
                onClicked = { navTo(actionHomeFragmentToVisualInstructionFragment()) }
            ),
            HomeListItem.ButtonItem(
                buttonTextResId = R.string.object_recognition,
                buttonIconResId = R.drawable.ic_diamond,
                onClicked = {
                    doIfPermissionGranted(
                        resultLauncher = micResultLauncher,
                        permission = android.Manifest.permission.RECORD_AUDIO,
                        block = { navTo(actionHomeFragmentToObjectRecognitionFragment()) }
                    )
                }
            ),
            HomeListItem.ButtonItem(
                buttonTextResId = R.string.sounds,
                buttonIconResId = dev.sertan.android.ram.core.ui.R.drawable.ic_mic,
                onClicked = {
                    doIfPermissionGranted(
                        resultLauncher = micResultLauncher,
                        permission = android.Manifest.permission.RECORD_AUDIO,
                        block = { navTo(actionHomeFragmentToVoiceImitationFragment()) }
                    )
                }
            ),
            HomeListItem.ButtonItem(
                buttonTextResId = R.string.body_parts,
                buttonIconResId = R.drawable.ic_people,
                onClicked = { navTo(actionHomeFragmentToBodyPartsFragment()) }
            ),
            HomeListItem.ButtonItem(
                buttonTextResId = dev.sertan.android.ram.core.ui.R.string.drawing,
                buttonIconResId = dev.sertan.android.ram.core.ui.R.drawable.ic_brush,
                onClicked = { navTo(actionHomeFragmentToDrawingFragment()) }
            )
        )
}
