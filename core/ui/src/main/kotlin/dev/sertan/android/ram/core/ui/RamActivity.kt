/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import dev.sertan.android.ram.core.ui.fragment.SplashFragment
import kotlinx.coroutines.delay

abstract class RamActivity(@LayoutRes layoutResId: Int) : AppCompatActivity(layoutResId) {

    abstract val navHostFragmentId: Int
    abstract val afterSplashDirection: NavDirections
    abstract val projectInformationDirection: NavDirections

    @get:DrawableRes
    abstract val appImageResId: Int

    private val viewModel by viewModels<RamActivityViewModel>()
    private val navController by lazy { findNavController(navHostFragmentId) }

    private val navigateAfterSplash = suspend {
        delay(SplashFragment.DEFAULT_DURATION_MS)
        navController.navigate(afterSplashDirection)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed(): Unit = with(navController) {
            if (backQueue.filterNot { it.destination.label.isNullOrEmpty() }.size > 1) {
                popBackStack()
                return
            }
            navigate(projectInformationDirection)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onStart() {
        super.onStart()
        viewModel.runOnce(navigateAfterSplash)
    }
}
