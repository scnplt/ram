/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.delay

abstract class RamActivity : AppCompatActivity() {

    protected inline fun launchAfterSplashDelay(crossinline block: () -> Unit) {
        lifecycleScope.launchWhenStarted {
            delay(SplashFragment.DEFAULT_DURATION_MS)
            block()
        }
    }

    protected fun provideNavController(navHostFragmentId: Int) = lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(navHostFragmentId) as? NavHostFragment
        navHostFragment?.navController
    }
}
