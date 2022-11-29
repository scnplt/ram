/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.util.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import kotlinx.coroutines.delay

fun AppCompatActivity.navigateAfterDelay(
    delayMillis: Long,
    navController: NavController,
    direction: NavDirections
) {
    lifecycleScope.launchWhenStarted {
        delay(delayMillis)
        navController.navigate(direction)
    }
}
