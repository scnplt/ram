/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.util

import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.sertan.android.ram.core.ui.R
import dev.sertan.android.ram.core.ui.RamActivity
import kotlin.properties.ReadOnlyProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Suppress("unused")
fun <VB : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> VB
): ReadOnlyProperty<Fragment, VB> = FragmentViewBindingDelegate(viewBindingFactory)

fun Fragment.repeatOnLifecycleStarted(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch { repeatOnLifecycle(Lifecycle.State.STARTED, block) }
}

fun Fragment.navigateToOssLicenses(): Unit =
    startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))

fun Fragment.doIfPermissionGranted(
    resultLauncher: ActivityResultLauncher<String>,
    permission: String,
    block: () -> Unit
) {
    val permissionResult = ContextCompat.checkSelfPermission(requireContext(), permission)
    if (permissionResult == PackageManager.PERMISSION_GRANTED) return block()
    resultLauncher.launch(permission)
}

fun Fragment.playSound(@RawRes soundResId: Int, onCompleteListener: () -> Unit = {}) {
    (requireActivity() as? RamActivity)?.playSound(soundResId, onCompleteListener)
}

fun Fragment.playNegativeSound() {
    (requireActivity() as? RamActivity)?.playSound(R.raw.negative)
}
