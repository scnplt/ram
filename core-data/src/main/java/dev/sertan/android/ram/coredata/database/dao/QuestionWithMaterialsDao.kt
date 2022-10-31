/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.sertan.android.ram.coredata.model.QuestionWithMaterialsEntity

@Dao
internal interface QuestionWithMaterialsDao {

    @Query("SELECT * FROM question_materials WHERE :questionId == questionId")
    suspend fun getAllById(questionId: Long): List<QuestionWithMaterialsEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg questionWithMaterialsEntityArray: QuestionWithMaterialsEntity)

    @Delete
    suspend fun delete(vararg questionWithMaterialsEntityArray: QuestionWithMaterialsEntity)

    @Update
    suspend fun update(questionWithMaterialsEntity: QuestionWithMaterialsEntity)
}
