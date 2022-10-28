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

fun SharedPreferences.getStringAsStream(dataKey: String): Flow<String?> = callbackFlow {
    trySend(getString(dataKey, null))
    val sharedPrefListener = OnSharedPreferenceChangeListener { sharedPref, key ->
        if (dataKey == key) trySend(sharedPref.getString(dataKey, null))
    }
    registerOnSharedPreferenceChangeListener(sharedPrefListener)
    awaitClose { unregisterOnSharedPreferenceChangeListener(sharedPrefListener) }
}
