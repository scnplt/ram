/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.ui

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appselection.NavGraphDirections.Companion.actionGlobalHomeFragment
import dev.sertan.android.ram.appselection.R
import dev.sertan.android.ram.core.ui.RamActivity

@AndroidEntryPoint
internal class MainActivity : RamActivity() {
    private val navController by provideNavController(R.id.fragmentContainerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchAfterSplashDelay { navController?.navigate(actionGlobalHomeFragment()) }
    }
}
