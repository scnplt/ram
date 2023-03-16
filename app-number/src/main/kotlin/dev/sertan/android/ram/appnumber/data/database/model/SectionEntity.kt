/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.data.database.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "sections")
internal data class SectionEntity(
    @PrimaryKey val uid: String,
    val minNumber: Int,
    val maxNumber: Int,
    val step: Int
)
