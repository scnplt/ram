/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sertan.android.ram.appmemory.data.datasource.local.dao.GapFillingQuestionDao
import dev.sertan.android.ram.appmemory.data.datasource.local.dao.MatchingQuestionDao
import dev.sertan.android.ram.appmemory.data.datasource.local.model.GapFillingQuestionEntity
import dev.sertan.android.ram.appmemory.data.datasource.local.model.MatchingQuestionEntity
import dev.sertan.android.ram.core.data.util.StringToJsonConverter

@Database(
    version = 1,
    exportSchema = false,
    entities = [GapFillingQuestionEntity::class, MatchingQuestionEntity::class]
)
@TypeConverters(StringToJsonConverter::class)
internal abstract class RamDatabase : RoomDatabase() {
    abstract fun gapFillingQuestionDao(): GapFillingQuestionDao
    abstract fun matchingQuestionDao(): MatchingQuestionDao
}
