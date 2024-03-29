/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui

import androidx.navigation.NavDirections
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appmemory.NavGraphDirections.Companion.actionGlobalHomeFragment
import dev.sertan.android.ram.appmemory.NavGraphDirections.Companion.actionGlobalProjectInfoFragment
import dev.sertan.android.ram.appmemory.R
import dev.sertan.android.ram.core.ui.RamActivity

@AndroidEntryPoint
internal class MainActivity : RamActivity(R.layout.activity_main) {
    override val navHostFragmentId: Int = R.id.fragmentContainerView
    override val afterSplashDirection: NavDirections = actionGlobalHomeFragment()
    override val projectInformationDirection: NavDirections = actionGlobalProjectInfoFragment()
    override val appImageResId: Int = R.drawable.ic_splash
}
