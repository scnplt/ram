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
import dev.sertan.android.ram.appmemory.data.datasource.local.model.MatchingQuestionEntity

@Dao
internal interface MatchingQuestionDao {

    @Query("SELECT * FROM matching_questions")
    suspend fun getAll(): List<MatchingQuestionEntity>

    @Query("DELETE FROM matching_questions")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(matchingQuestionEntity: MatchingQuestionEntity)
}
