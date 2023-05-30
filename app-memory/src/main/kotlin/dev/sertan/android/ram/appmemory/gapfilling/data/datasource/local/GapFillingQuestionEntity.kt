/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.gapfilling.data.datasource.local

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "gap_filling_questions")
internal data class GapFillingQuestionEntity(
    @PrimaryKey val uid: String,
    val content: String,
    val correctMaterialUid: String,
    val materialUidList: List<String>,
    val contentMaterialUid: String
)
