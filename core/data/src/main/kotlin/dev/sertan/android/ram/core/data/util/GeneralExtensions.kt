/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.util

import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

private const val COLLECTION_GROUP_NAME_KEY = "collection-group-name-key"

fun SharedPreferences.getStringAsStream(dataKey: String): Flow<String?> = callbackFlow {
    trySend(getString(dataKey, null))
    val sharedPrefListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPref, key ->
        if (dataKey == key) trySend(sharedPref.getString(dataKey, null))
    }
    registerOnSharedPreferenceChangeListener(sharedPrefListener)
    awaitClose { unregisterOnSharedPreferenceChangeListener(sharedPrefListener) }
}

fun SharedPreferences.getSavedCollectionGroupName(): String? =
    getString(COLLECTION_GROUP_NAME_KEY, null)

fun SharedPreferences.setCollectionGroupName(newName: String): Unit =
    edit().putString(COLLECTION_GROUP_NAME_KEY, newName).apply()
