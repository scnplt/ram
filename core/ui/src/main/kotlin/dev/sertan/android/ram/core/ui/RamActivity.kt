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
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.delay

abstract class RamActivity(@LayoutRes layoutResId: Int) : AppCompatActivity(layoutResId) {
    abstract val label: String
    abstract val navHostFragmentId: Int
    abstract val afterSplashDirection: NavDirections

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(navHostFragmentId) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            delay(SplashFragment.DEFAULT_DURATION_MS)
            navController.navigate(afterSplashDirection)
        }
    }
}
