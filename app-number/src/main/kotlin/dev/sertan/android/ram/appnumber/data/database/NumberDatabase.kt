/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sertan.android.ram.appnumber.data.database.dao.MaterialDao
import dev.sertan.android.ram.appnumber.data.database.dao.QuestionDao
import dev.sertan.android.ram.appnumber.data.database.dao.SectionDao
import dev.sertan.android.ram.appnumber.data.database.model.MaterialEntity
import dev.sertan.android.ram.appnumber.data.database.model.QuestionEntity
import dev.sertan.android.ram.appnumber.data.database.model.SectionEntity
import dev.sertan.android.ram.core.data.util.StringToJsonConverter

@Database(
    version = 1,
    exportSchema = false,
    entities = [MaterialEntity::class, QuestionEntity::class, SectionEntity::class]
)
@TypeConverters(StringToJsonConverter::class)
internal abstract class NumberDatabase : RoomDatabase() {
    abstract fun materialDao(): MaterialDao
    abstract fun questionDao(): QuestionDao
    abstract fun sectionDao(): SectionDao
}
