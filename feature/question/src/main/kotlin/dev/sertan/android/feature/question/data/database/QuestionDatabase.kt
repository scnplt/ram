/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.feature.question.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sertan.android.feature.question.data.database.dao.QuestionDao
import dev.sertan.android.feature.question.data.database.model.QuestionEntity
import dev.sertan.android.ram.core.data.util.StringToJsonConverter

@Database(version = 1, exportSchema = false, entities = [QuestionEntity::class])
@TypeConverters(StringToJsonConverter::class)
internal abstract class QuestionDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}
