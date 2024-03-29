/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appletter.ui.home

import androidx.navigation.NavDirections
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appletter.R
import dev.sertan.android.ram.appletter.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToDrawingFragment
import dev.sertan.android.ram.appletter.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToPracticeGraph
import dev.sertan.android.ram.appletter.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToTrainingFragment
import dev.sertan.android.ram.appletter.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToWriteFragment
import dev.sertan.android.ram.core.ui.util.extension.labelWithoutPrefix
import dev.sertan.android.ram.core.ui.util.extension.navTo
import dev.sertan.android.ram.feature.home.adapter.HomeListItem
import dev.sertan.android.ram.feature.training.TrainingHomeFragment

@AndroidEntryPoint
internal class HomeFragment : TrainingHomeFragment() {

    override var directionAfterFinished: NavDirections? = actionHomeFragmentToPracticeGraph()

    override val items: List<HomeListItem>
        get() = listOf(
            HomeListItem.TitleItem(title = requireContext().labelWithoutPrefix),
            HomeListItem.HeaderItem(iconResId = R.drawable.ic_splash),
            HomeListItem.ButtonItem(
                buttonTextResId = dev.sertan.android.ram.core.ui.R.string.training,
                buttonIconResId = dev.sertan.android.ram.core.ui.R.drawable.ic_play,
                onClicked = { navTo(actionHomeFragmentToTrainingFragment()) }
            ),
            HomeListItem.ButtonItem(
                buttonTextResId = dev.sertan.android.ram.core.ui.R.string.practice,
                buttonIconResId = dev.sertan.android.ram.core.ui.R.drawable.ic_pen,
                onClicked = { navTo(actionHomeFragmentToPracticeGraph()) }
            ),
            HomeListItem.ButtonItem(
                buttonTextResId = R.string.writing_practice,
                buttonIconResId = R.drawable.ic_draw,
                onClicked = { navTo(actionHomeFragmentToWriteFragment()) }
            ),
            HomeListItem.ButtonItem(
                buttonTextResId = dev.sertan.android.ram.core.ui.R.string.drawing,
                buttonIconResId = dev.sertan.android.ram.core.ui.R.drawable.ic_brush,
                onClicked = { navTo(actionHomeFragmentToDrawingFragment()) }
            )
        )
}
