/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.util.extension

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import dev.sertan.android.ram.core.util.FragmentViewBindingDelegate
import kotlin.properties.ReadOnlyProperty

fun <VB : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> VB
): ReadOnlyProperty<Fragment, VB> = FragmentViewBindingDelegate(viewBindingFactory)

fun Fragment.navigateTo(navDirections: NavDirections): Unit =
    findNavController().navigate(navDirections)

fun Fragment.popBackStack() {
    findNavController().popBackStack()
}

fun Fragment.showToast(message: String?): Unit = requireContext().showToast(message)
