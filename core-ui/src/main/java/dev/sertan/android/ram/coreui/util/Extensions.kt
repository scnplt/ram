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
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.AnimRes
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

fun View.startAnimation(@AnimRes animationId: Int) {
    startAnimation(AnimationUtils.loadAnimation(context, animationId))
}

fun TextToSpeech.speak(message: CharSequence) {
    speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
}
