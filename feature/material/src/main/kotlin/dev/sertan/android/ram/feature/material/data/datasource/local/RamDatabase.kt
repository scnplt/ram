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
import androidx.room.Database
import androidx.room.RoomDatabase

@Keep
@Database(
    version = 1,
    exportSchema = false,
    entities = [MaterialEntity::class]
)
internal abstract class RamDatabase : RoomDatabase() {
    abstract fun materialDao(): MaterialDao
}
