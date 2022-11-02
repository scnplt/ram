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
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
internal data class QuestionEntity(
    @PrimaryKey val questionUid: String,
    val content: String,
    val answerMaterialId: Long,
)
