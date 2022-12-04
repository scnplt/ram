/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import dev.sertan.android.ram.core.domain.usecase.VoiceSupportUseCase
import dev.sertan.android.ram.core.ui.UncaughtExceptionHandlerActivity.Companion.EXTRA_EXCEPTION_MESSAGE
import javax.inject.Inject
import kotlinx.coroutines.delay
import timber.log.Timber

abstract class RamActivity(@LayoutRes layoutRes: Int) : AppCompatActivity(layoutRes) {

    @Inject
    lateinit var voiceSupportUseCase: VoiceSupportUseCase

    private val uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, throwable ->
        Timber.e(throwable)
        val intent = Intent(this, UncaughtExceptionHandlerActivity::class.java).apply {
            putExtra(EXTRA_EXCEPTION_MESSAGE, throwable.localizedMessage)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler)
    }
}
