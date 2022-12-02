/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import dev.sertan.android.ram.core.domain.usecase.VoiceSupportUseCase
import javax.inject.Inject
import kotlinx.coroutines.delay

abstract class RamActivity(@LayoutRes layoutRes: Int) : AppCompatActivity(layoutRes) {

    @Inject
    lateinit var voiceSupportUseCase: VoiceSupportUseCase

    protected fun getNavController(navHostFragmentId: Int) = lazy {
        val fragment = supportFragmentManager.findFragmentById(navHostFragmentId)
        (fragment as? NavHostFragment)?.navController
    }

    protected inline fun launchAfterSplashDelay(crossinline block: () -> Unit) {
        lifecycleScope.launchWhenStarted {
            delay(SplashFragment.DEFAULT_DURATION_MS)
            block()
        }
    }
}
