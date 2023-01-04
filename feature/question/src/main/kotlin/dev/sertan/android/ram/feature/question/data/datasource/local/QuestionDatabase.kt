/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.question.data.datasource.local

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sertan.android.ram.core.data.util.StringToJsonConverter

@Keep
@Database(version = 1, exportSchema = false, entities = [QuestionEntity::class])
@TypeConverters(StringToJsonConverter::class)
internal abstract class QuestionDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}
