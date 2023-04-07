/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training.data.datasource.local

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sertan.android.ram.core.data.util.StringToJsonConverter
import dev.sertan.android.ram.feature.training.data.datasource.local.dao.MaterialDao
import dev.sertan.android.ram.feature.training.data.datasource.local.dao.QuestionDao
import dev.sertan.android.ram.feature.training.data.datasource.local.model.MaterialEntity
import dev.sertan.android.ram.feature.training.data.datasource.local.model.QuestionEntity

@Keep
@Database(
    version = 1,
    exportSchema = false,
    entities = [QuestionEntity::class, MaterialEntity::class]
)
@TypeConverters(StringToJsonConverter::class)
internal abstract class RamDatabase : RoomDatabase() {
    abstract fun materialDao(): MaterialDao
    abstract fun questionDao(): QuestionDao
}
