/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.material.data.datasource.local

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "materials")
internal data class MaterialEntity(
    @PrimaryKey val uid: String,
    val description: String,
    val mediaUrl: String,
    val attribution: String?,
    val isShownByDefault: Boolean
)
