/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcolor.NavMainDirections.Companion.actionGlobalHomeFragment
import dev.sertan.android.ram.appcolor.R
import dev.sertan.android.ram.coredomain.usecase.VoiceSupportUseCase
import javax.inject.Inject
import kotlinx.coroutines.delay

private const val SPLASH_FRAGMENT_DURATION_MS = 3000L

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var voiceSupportUseCase: VoiceSupportUseCase

    private val navController by lazy {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        (fragment as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            delay(SPLASH_FRAGMENT_DURATION_MS)
            navController.navigate(actionGlobalHomeFragment())
        }
    }
}
