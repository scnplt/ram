/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.util.extension

import android.content.SharedPreferences
import android.content.res.Resources
import android.speech.tts.TextToSpeech
import androidx.annotation.ArrayRes
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber

private const val NO_COLOR = Int.MIN_VALUE

fun SharedPreferences.getStringAsStream(dataKey: String): Flow<String?> = callbackFlow {
    trySend(getString(dataKey, null))
    val sharedPrefListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPref, key ->
        if (dataKey == key) trySend(sharedPref.getString(dataKey, null))
    }
    registerOnSharedPreferenceChangeListener(sharedPrefListener)
    awaitClose { unregisterOnSharedPreferenceChangeListener(sharedPrefListener) }
}

fun <I, O> Flow<I>.tryGetResultWithLog(block: (I) -> O): Flow<Result<O>> =
    map { Result.success(block(it)) }.catch { exception ->
        Timber.e(exception)
        Result.failure<O>(exception)
    }

@Suppress("DEPRECATION")
fun TextToSpeech.speak(message: String?) {
    speak(message?.replace("\\s+".toRegex(), ", ") ?: return, TextToSpeech.QUEUE_FLUSH, null)
}

fun Resources.getColorList(@ArrayRes colorListId: Int): List<Int> = obtainTypedArray(colorListId)
    .use {
        (0 until it.length()).mapNotNull { colorIndex ->
            it.getColor(colorIndex, NO_COLOR).takeIf { color -> color != NO_COLOR }
        }
    }
