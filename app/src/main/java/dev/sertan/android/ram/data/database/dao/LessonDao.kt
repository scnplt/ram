/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sertan.android.ram.data.datasource.LessonDataSource
import dev.sertan.android.ram.data.model.LessonEntity

@Dao
internal interface LessonDao : LessonDataSource {

    @Query("SELECT * FROM lessons")
    override suspend fun getLessons(): List<LessonEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun createLesson(vararg lessonEntity: LessonEntity)

    @Delete
    override suspend fun deleteLesson(vararg lessonEntity: LessonEntity)
}
