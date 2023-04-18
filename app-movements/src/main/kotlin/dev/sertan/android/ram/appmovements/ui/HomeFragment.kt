/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.ui

import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appmovements.R
import dev.sertan.android.ram.appmovements.ui.HomeFragmentDirections.Companion.actionHomeFragmentToDrawingFragment
import dev.sertan.android.ram.appmovements.ui.HomeFragmentDirections.Companion.actionHomeFragmentToPoseDetectionFragment
import dev.sertan.android.ram.core.ui.util.extension.doIfPermissionGranted
import dev.sertan.android.ram.core.ui.util.extension.labelWithoutPrefix
import dev.sertan.android.ram.core.ui.util.extension.navTo
import dev.sertan.android.ram.core.ui.util.extension.resultLauncher
import dev.sertan.android.ram.core.ui.util.extension.showToast
import dev.sertan.android.ram.feature.home.BaseHomeFragment
import dev.sertan.android.ram.feature.home.adapter.HomeListItem

@AndroidEntryPoint
internal class HomeFragment : BaseHomeFragment() {

    private val cameraResultLauncher = resultLauncher(
        onGranted = { navTo(actionHomeFragmentToPoseDetectionFragment()) },
        onDenied = { showToast(dev.sertan.android.ram.core.ui.R.string.camera_permission_denied) }
    )

    override val items: List<HomeListItem>
        get() = listOf(
            HomeListItem.TitleItem(title = requireContext().labelWithoutPrefix),
            HomeListItem.HeaderItem(iconResId = R.drawable.ic_splash),
            HomeListItem.ButtonItem(
                buttonTextResId = R.string.start,
                buttonIconResId = dev.sertan.android.ram.core.ui.R.drawable.ic_play,
                onClicked = {
                    doIfPermissionGranted(
                        resultLauncher = cameraResultLauncher,
                        permission = android.Manifest.permission.CAMERA,
                        block = { navTo(actionHomeFragmentToPoseDetectionFragment()) }
                    )
                }
            ),
            HomeListItem.ButtonItem(
                buttonTextResId = dev.sertan.android.ram.core.ui.R.string.drawing,
                buttonIconResId = dev.sertan.android.ram.core.ui.R.drawable.ic_brush,
                onClicked = { navTo(actionHomeFragmentToDrawingFragment()) }
            )
        )
}
