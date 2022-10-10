/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.database.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.sertan.android.ram.data.model.AnswerEntity
import dev.sertan.android.ram.data.model.LessonEntity
import dev.sertan.android.ram.data.model.MaterialEntity
import dev.sertan.android.ram.data.model.QuestionEntity
import dev.sertan.android.ram.data.model.QuestionOptionEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        LessonEntity::class,
        MaterialEntity::class,
        QuestionEntity::class,
        QuestionOptionEntity::class,
        AnswerEntity::class
    ]
)
internal abstract class LessonDatabase : RoomDatabase() {
    abstract fun lessonDao(): LessonDao
}
