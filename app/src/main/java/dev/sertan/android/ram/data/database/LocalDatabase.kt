/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.sertan.android.ram.data.database.dao.LessonDao
import dev.sertan.android.ram.data.database.dao.MaterialDao
import dev.sertan.android.ram.data.database.dao.QuestionDao
import dev.sertan.android.ram.data.model.LessonEntity
import dev.sertan.android.ram.data.model.MaterialEntity
import dev.sertan.android.ram.data.model.QuestionEntity

@Database(
    version = 1,
    entities = [
        LessonEntity::class,
        QuestionEntity::class,
        MaterialEntity::class
    ]
)
internal abstract class LocalDatabase : RoomDatabase() {
    abstract fun lessonDao(): LessonDao
    abstract fun questionDao(): QuestionDao
    abstract fun materialDao(): MaterialDao
}
