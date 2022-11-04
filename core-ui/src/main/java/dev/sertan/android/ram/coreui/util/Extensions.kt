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
import android.content.res.Resources
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.annotation.ArrayRes

private const val NO_COLOR = Int.MIN_VALUE

fun Context.showToast(message: String?) {
    message?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
}

fun Resources.getColorList(@ArrayRes colorListId: Int): List<Int> = obtainTypedArray(colorListId)
    .use {
        (0 until it.length()).mapNotNull { colorIndex ->
            it.getColor(colorIndex, NO_COLOR).takeIf { color -> color != NO_COLOR }
        }
    }

fun TextToSpeech.speak(message: String?) {
    speak(message?.replace("\\s+".toRegex(), ",") ?: return, TextToSpeech.QUEUE_FLUSH, null, "")
}
