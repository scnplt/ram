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
import dev.sertan.android.ram.data.model.MaterialEntity

@Dao
internal interface MaterialDao {

    @Query("SELECT * FROM materials")
    suspend fun getAll(): List<MaterialEntity>

    @Query("SELECT * FROM materials WHERE materialId IN (:materialIds)")
    suspend fun getAllWithIds(materialIds: List<Long>): List<MaterialEntity>

    @Query("SELECT * FROM materials WHERE :materialId == materialId")
    suspend fun getById(materialId: Long): MaterialEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg materialEntityArray: MaterialEntity)

    @Delete
    suspend fun delete(vararg materialEntityArray: MaterialEntity)

    @Update
    suspend fun update(materialEntity: MaterialEntity)
}
