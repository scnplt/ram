/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.util

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.AnimRes
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty

fun Context.showToast(message: String?) {
    message?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
}

fun <VB : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> VB
): ReadOnlyProperty<Fragment, VB> = FragmentViewBindingDelegate(viewBindingFactory)

fun Fragment.navigateTo(navDirections: NavDirections) {
    findNavController().navigate(navDirections)
}

fun Fragment.showToast(message: String?): Unit = requireContext().showToast(message)

fun Fragment.getColor(@ColorRes colorId: Int): Int = requireContext().getColor(colorId)

fun View.startAnimation(@AnimRes animationId: Int) {
    startAnimation(context.getAnimation(animationId))
}

fun Context.getAnimation(@AnimRes animationId: Int): Animation {
    return AnimationUtils.loadAnimation(this, animationId)
}
