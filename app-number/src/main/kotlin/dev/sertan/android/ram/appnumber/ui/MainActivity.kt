/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.ui

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavDirections
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appnumber.NavGraphDirections.Companion.actionGlobalHomeFragment
import dev.sertan.android.ram.appnumber.NavGraphDirections.Companion.actionGlobalProjectInfoFragment
import dev.sertan.android.ram.appnumber.R
import dev.sertan.android.ram.core.ui.RamActivity
import dev.sertan.android.ram.core.ui.util.goToApplicationSettings

@AndroidEntryPoint
internal class MainActivity : RamActivity(R.layout.activity_main) {

    override val navHostFragmentId: Int = R.id.fragmentContainerView

    override val afterSplashDirection: NavDirections = actionGlobalHomeFragment()

    override val projectInformationDirection: NavDirections = actionGlobalProjectInfoFragment()

    private val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (!isGranted) showPermissionDetailDialog()
        }

    private fun showPermissionDetailDialog() {
        // TODO: Refactor this
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Uygulama izinlerini neden kabul etmelisiniz?")
            .setMessage(
                "Sesli desteği aktif etmezseniz uygulamanın asıl amacı olan sesli " +
                    "soruları ve sesli yanıt sistemini kullanamazsınız!"
            )
            .setCancelable(false)
            .setPositiveButton("İzinlere git") { _, _ -> goToApplicationSettings() }
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }
}
