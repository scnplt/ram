/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.extension

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <VB : ViewBinding> Fragment.provideBinding(
    viewBindingProvider: (View) -> VB
): ReadOnlyProperty<Fragment, VB> {
    return object : ReadOnlyProperty<Fragment, VB> {
        private var binding: VB? = null

        private val lifecycleObserver = object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                binding = null
            }
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
            return binding ?: viewBindingProvider(thisRef.requireView()).also {
                binding = it
                viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
            }
        }
    }
}

fun Fragment.navigateTo(navDirections: NavDirections) {
    findNavController().navigate(navDirections)
}
