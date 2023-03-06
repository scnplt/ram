/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.util

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.navTo(navDirections: NavDirections): Unit = findNavController().navigate(navDirections)

fun Fragment.popBackStack(): Boolean = findNavController().popBackStack()

fun <T> Fragment.popBackStack(key: String, data: T): Boolean = with(findNavController()) {
    previousBackStackEntry?.savedStateHandle?.let {
        if (!it.contains(key)) it[key] = data
        popBackStack()
    } ?: false
}

fun <T> Fragment.savedStateHandeListener(key: String, callback: (T) -> Unit) {
    val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle ?: return
    savedStateHandle.getLiveData<T>(key).observe(viewLifecycleOwner) { data ->
        callback(data)
        savedStateHandle.remove<T>(key)
    }
}
