/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.util

import androidx.annotation.Keep
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Keep
class StringToJsonConverter {

    @TypeConverter
    fun stringListToJson(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun stringListFromJson(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }
}
