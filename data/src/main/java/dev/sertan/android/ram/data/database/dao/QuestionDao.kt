/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.sertan.android.ram.data.model.QuestionEntity

@Dao
internal interface QuestionDao {

    @Query("SELECT * FROM questions")
    suspend fun getAll(): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE :questionId == questionId")
    suspend fun getById(questionId: Long): QuestionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg questionEntityArray: QuestionEntity)

    @Delete
    suspend fun delete(vararg questionEntityArray: QuestionEntity)

    @Update
    suspend fun update(questionEntity: QuestionEntity)
}
