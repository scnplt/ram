/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

private const val DEFAULT_LESSON_TIME_IN_SECONDS = 300

@Entity(tableName = "lessons")
internal data class LessonEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String,

    @ColumnInfo(defaultValue = "$DEFAULT_LESSON_TIME_IN_SECONDS")
    val lessonTimeInSeconds: Int
)
