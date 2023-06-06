/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sertan.android.ram.appmemory.data.datasource.local.model.GapFillingQuestionEntity

@Dao
internal interface GapFillingQuestionDao {

    @Query("SELECT * FROM gap_filling_questions")
    suspend fun getAll(): List<GapFillingQuestionEntity>

    @Query("DELETE FROM gap_filling_questions")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gapFillingQuestionEntity: GapFillingQuestionEntity)
}
