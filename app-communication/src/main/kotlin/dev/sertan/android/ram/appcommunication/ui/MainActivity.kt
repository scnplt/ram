/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui

import androidx.navigation.NavDirections
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcommunication.NavGraphDirections.Companion.actionGlobalHomeFragment
import dev.sertan.android.ram.appcommunication.NavGraphDirections.Companion.actionGlobalProjectInfoFragment
import dev.sertan.android.ram.appcommunication.R
import dev.sertan.android.ram.core.domain.usecase.GetApplicationConfigsUseCase
import dev.sertan.android.ram.core.ui.RamActivity
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : RamActivity(R.layout.activity_main) {
    override val navHostFragmentId: Int = R.id.fragmentContainerView
    override val afterSplashDirection: NavDirections = actionGlobalHomeFragment()
    override val projectInformationDirection: NavDirections = actionGlobalProjectInfoFragment()
    override val appImageResId: Int = R.drawable.ic_splash

    @Inject
    lateinit var getApplicationConfigsUseCase: GetApplicationConfigsUseCase
}
