/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredomain.util

import android.speech.tts.TextToSpeech

@Suppress("DEPRECATION")
fun TextToSpeech.speak(message: String?) {
    speak(message?.replace("\\s+".toRegex(), ", ") ?: return, TextToSpeech.QUEUE_FLUSH, null)
}
