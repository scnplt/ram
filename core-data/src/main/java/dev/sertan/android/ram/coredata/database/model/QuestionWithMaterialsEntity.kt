/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question_materials",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = arrayOf("questionUid"),
            childColumns = arrayOf("questionUid"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MaterialEntity::class,
            parentColumns = arrayOf("materialUid"),
            childColumns = arrayOf("materialUid"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("questionUid"), Index("materialUid")]
)
internal data class QuestionWithMaterialsEntity(
    @PrimaryKey val uid: String,
    val questionUid: String,
    val materialUid: String
)
