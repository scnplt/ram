/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.corecommon.util

interface JsonConverter<T> {
    fun toJson(data: T): String
    fun fromJson(json: String): T?

    companion object {
        fun <T> create(toJson: (T) -> String, fromJson: (String) -> T?): JsonConverter<T> {
            return object : JsonConverter<T> {
                override fun toJson(data: T): String = toJson(data)
                override fun fromJson(json: String): T? = fromJson(json)
            }
        }
    }
}
