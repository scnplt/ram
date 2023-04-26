/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.util.extension

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.IdRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.sertan.android.ram.core.ui.R
import dev.sertan.android.ram.core.ui.RamActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.repeatOnLifecycleStarted(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch { repeatOnLifecycle(Lifecycle.State.STARTED, block) }
}

fun Fragment.navigateToOssLicenses(): Unit =
    startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))

fun Fragment.navTo(navDirections: NavDirections): Unit = findNavController().navigate(navDirections)

fun Fragment.navTo(@IdRes destinationResId: Int, args: Bundle? = null): Unit =
    findNavController().navigate(destinationResId, args)

fun Fragment.popBackStack(): Boolean = findNavController().popBackStack()

fun <T> Fragment.popBackStack(key: String, data: T): Boolean = with(findNavController()) {
    previousBackStackEntry?.savedStateHandle?.let {
        if (!it.contains(key)) it[key] = data
        popBackStack()
    } ?: false
}

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

fun Fragment.playCorrectSound(): Unit = playSound(R.raw.correct)

fun Fragment.playNegativeSound(): Unit = playSound(R.raw.negative)

fun <T> Fragment.savedStateHandeListener(key: String, callback: (T) -> Unit) {
    val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle ?: return
    savedStateHandle.getLiveData<T>(key).observe(viewLifecycleOwner) { data ->
        callback(data)
        savedStateHandle.remove<T>(key)
    }
}

fun Fragment.resultLauncher(
    onGranted: () -> Unit = {},
    onDenied: () -> Unit = {}
): ActivityResultLauncher<String> = registerForActivityResult(RequestPermission()) {
    if (it) onGranted() else onDenied()
}

fun Fragment.showToast(@StringRes messageResId: Int) =
    Toast.makeText(requireContext(), messageResId, Toast.LENGTH_SHORT).show()
