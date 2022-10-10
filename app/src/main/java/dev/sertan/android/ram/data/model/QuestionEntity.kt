/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "questions",
    foreignKeys = [ForeignKey(
        entity = LessonEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("lessonId"),
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class QuestionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val content: String,

    val lessonId: Int
)
