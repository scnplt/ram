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
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "questions",
    foreignKeys = [ForeignKey(
        entity = LessonEntity::class,
        parentColumns = arrayOf("lessonId"),
        childColumns = arrayOf("ownerLessonId"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
internal data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val questionId: Long,
    val ownerLessonId: Long,
    val question: String,
    val answerMaterialId: Long
)
