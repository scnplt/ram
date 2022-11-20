/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.corecommon.util

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber

fun SharedPreferences.getStringAsStream(dataKey: String): Flow<String?> = callbackFlow {
    trySend(getString(dataKey, null))
    val sharedPrefListener = OnSharedPreferenceChangeListener { sharedPref, key ->
        if (dataKey == key) trySend(sharedPref.getString(dataKey, null))
    }
    registerOnSharedPreferenceChangeListener(sharedPrefListener)
    awaitClose { unregisterOnSharedPreferenceChangeListener(sharedPrefListener) }
}

@Suppress("TooGenericExceptionCaught")
suspend fun <R> tryGetResultWithLog(block: suspend () -> R): Result<R> = try {
    Result.success(block())
} catch (exception: Throwable) {
    Timber.e(exception)
    Result.failure(exception)
}

fun <I, O> Flow<I>.tryGetResultWithLog(block: (I) -> O): Flow<Result<O>> =
    map { Result.success(block(it)) }.catch { exception ->
        Timber.e(exception)
        Result.failure<O>(exception)
    }

@Suppress("MagicNumber")
fun percent(value: Float, total: Float): Float = value / total * 100

@Suppress("MagicNumber")
fun percent(value: Int, total: Int): Int = value / total * 100
