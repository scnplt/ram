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
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question_materials",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = arrayOf("questionId"),
            childColumns = arrayOf("questionId"),
            onDelete = CASCADE,
            onUpdate = CASCADE
        ),
        ForeignKey(
            entity = MaterialEntity::class,
            parentColumns = arrayOf("materialId"),
            childColumns = arrayOf("materialId"),
            onDelete = CASCADE,
            onUpdate = CASCADE
        ),
    ],
    indices = [Index("questionId"), Index("materialId")]
)
internal data class QuestionWithMaterialsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val questionId: Long,
    val materialId: Long
)
