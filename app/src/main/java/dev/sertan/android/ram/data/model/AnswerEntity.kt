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
    tableName = "answers",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("questionId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = QuestionOptionEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("optionId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
)
internal data class AnswerEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val questionId: Int,

    val optionId: Int
)
