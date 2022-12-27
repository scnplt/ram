/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.sertan.android.ram.feature.training.data.database.dao.MaterialDao
import dev.sertan.android.ram.feature.training.data.database.model.MaterialEntity

@Database(version = 1, exportSchema = false, entities = [MaterialEntity::class])
internal abstract class TrainingDatabase : RoomDatabase() {
    abstract fun materialDao(): MaterialDao
}
