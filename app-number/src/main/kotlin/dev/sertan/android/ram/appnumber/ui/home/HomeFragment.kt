/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.ui.home

import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appnumber.R
import dev.sertan.android.ram.appnumber.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToCountingFragment
import dev.sertan.android.ram.appnumber.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToDrawingFragment
import dev.sertan.android.ram.appnumber.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToPracticeGraph
import dev.sertan.android.ram.core.ui.util.extension.doIfPermissionGranted
import dev.sertan.android.ram.core.ui.util.extension.labelWithoutPrefix
import dev.sertan.android.ram.core.ui.util.extension.navTo
import dev.sertan.android.ram.core.ui.util.extension.resultLauncher
import dev.sertan.android.ram.core.ui.util.extension.showToast
import dev.sertan.android.ram.feature.home.adapter.HomeListItem
import dev.sertan.android.ram.feature.training.TrainingHomeFragment
import dev.sertan.android.ram.feature.training.ui.training.TrainingFragment.Companion.SHUFFLE_KEY

@AndroidEntryPoint
internal class HomeFragment : TrainingHomeFragment() {

    override var directionAfterFinished: NavDirections? = actionHomeFragmentToPracticeGraph()

    private val micResultLauncher = resultLauncher(
        onGranted = { navTo(actionHomeFragmentToCountingFragment()) },
        onDenied = { showToast(dev.sertan.android.ram.core.ui.R.string.mic_permission_denied) }
    )

    override val items: List<HomeListItem>
        get() = listOf(
            HomeListItem.TitleItem(title = requireContext().labelWithoutPrefix),
            HomeListItem.HeaderItem(iconResId = R.drawable.ic_splash),
            HomeListItem.ButtonItem(
                buttonTextResId = dev.sertan.android.ram.core.ui.R.string.training,
                buttonIconResId = dev.sertan.android.ram.core.ui.R.drawable.ic_play,
                onClicked = {
                    navTo(destinationResId = R.id.trainingFragment, bundleOf(SHUFFLE_KEY to false))
                }
            ),
            HomeListItem.ButtonItem(
                buttonTextResId = dev.sertan.android.ram.core.ui.R.string.practice,
                buttonIconResId = dev.sertan.android.ram.core.ui.R.drawable.ic_pen,
                onClicked = { navTo(actionHomeFragmentToPracticeGraph()) }
            ),
            HomeListItem.ButtonItem(
                buttonTextResId = R.string.sequential_counting,
                buttonIconResId = dev.sertan.android.ram.core.ui.R.drawable.ic_mic,
                onClicked = {
                    doIfPermissionGranted(
                        resultLauncher = micResultLauncher,
                        permission = android.Manifest.permission.RECORD_AUDIO,
                        block = { navTo(actionHomeFragmentToCountingFragment()) }
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
