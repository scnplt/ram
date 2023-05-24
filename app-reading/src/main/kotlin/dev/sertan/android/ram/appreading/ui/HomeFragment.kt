/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appreading.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appreading.R
import dev.sertan.android.ram.appreading.ui.HomeFragmentDirections.Companion.actionHomeFragmentToDrawingFragment
import dev.sertan.android.ram.appreading.ui.HomeFragmentDirections.Companion.actionHomeFragmentToPracticeGraph
import dev.sertan.android.ram.core.ui.util.extension.labelWithoutPrefix
import dev.sertan.android.ram.core.ui.util.extension.navTo
import dev.sertan.android.ram.core.ui.util.extension.savedStateHandeListener
import dev.sertan.android.ram.feature.home.BaseHomeFragment
import dev.sertan.android.ram.feature.home.adapter.HomeListItem
import dev.sertan.android.ram.feature.training.ui.training.TrainingFragment
import dev.sertan.android.ram.feature.training.ui.training.TrainingFragment.Companion.SHUFFLE_KEY

@AndroidEntryPoint
internal class HomeFragment : BaseHomeFragment() {

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
                buttonTextResId = dev.sertan.android.ram.core.ui.R.string.drawing,
                buttonIconResId = dev.sertan.android.ram.core.ui.R.drawable.ic_brush,
                onClicked = { navTo(actionHomeFragmentToDrawingFragment()) }
            )
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedStateHandeListener<Boolean>(TrainingFragment.KEY_FINISHED) {
            if (it) navTo(actionHomeFragmentToPracticeGraph())
        }
    }
}
