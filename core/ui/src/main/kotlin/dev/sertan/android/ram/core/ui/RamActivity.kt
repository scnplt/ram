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
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import dev.sertan.android.ram.core.ui.fragment.SplashFragment
import kotlinx.coroutines.delay

abstract class RamActivity(@LayoutRes layoutResId: Int) : AppCompatActivity(layoutResId) {
    abstract val navHostFragmentId: Int
    abstract val afterSplashDirection: NavDirections

    protected open val projectInformationDirection: NavDirections? = null

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(navHostFragmentId) as NavHostFragment
        navHostFragment.navController
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed(): Unit = with(navController) {
            if (backQueue.size > 2) {
                popBackStack()
                return
            }
            projectInformationDirection?.let(navController::navigate) ?: finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        lifecycleScope.launchWhenStarted {
            delay(SplashFragment.DEFAULT_DURATION_MS)
            navController.navigate(afterSplashDirection)
        }
    }
}
