/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.sertan.android.ram.coredata.database.dao.MaterialDao
import dev.sertan.android.ram.coredata.database.dao.QuestionDao
import dev.sertan.android.ram.coredata.database.dao.QuestionWithMaterialsDao
import dev.sertan.android.ram.coredata.model.MaterialEntity
import dev.sertan.android.ram.coredata.model.QuestionEntity
import dev.sertan.android.ram.coredata.model.QuestionWithMaterialsEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        QuestionEntity::class,
        MaterialEntity::class,
        QuestionWithMaterialsEntity::class
    ]
)
internal abstract class RamDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun materialDao(): MaterialDao
    abstract fun questionWithMaterialsDao(): QuestionWithMaterialsDao
}
