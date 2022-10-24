/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.util

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import dev.sertan.android.ram.corecommon.util.JsonConverter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.onStart

internal class SharedPrefUtility<T>(
    private val sharedPreferences: SharedPreferences,
    private val jsonConverter: JsonConverter<T>,
    dataClass: Class<T>,
    defaultKey: String? = null
) {

    private val dataKey: String = defaultKey ?: dataClass.simpleName

    fun putData(data: T): Unit = sharedPreferences.edit()
        .putString(dataKey, jsonConverter.toJson(data))
        .apply()

    @Throws(NullPointerException::class)
    fun getData(): T = getJson()!!.let(jsonConverter::fromJson)!!

    fun getDataAsStream(): Flow<T?> = channelFlow {
        val sharedPrefListener = getSharedPrefChangeListener(onChanged = ::trySend)
        with(sharedPreferences) {
            registerOnSharedPreferenceChangeListener(sharedPrefListener)
            awaitClose { unregisterOnSharedPreferenceChangeListener(sharedPrefListener) }
        }
    }.onStart { getJson()?.let { emit(jsonConverter.fromJson(it)) } }

    private fun getJson(): String? = sharedPreferences.getString(dataKey, null)

    private fun getSharedPrefChangeListener(
        onChanged: (T?) -> Unit
    ): OnSharedPreferenceChangeListener = OnSharedPreferenceChangeListener { sharedPref, key ->
        if (key != dataKey) return@OnSharedPreferenceChangeListener
        sharedPref.getString(dataKey, null)?.let { json -> onChanged(jsonConverter.fromJson(json)) }
    }
}
