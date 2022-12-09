/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sertan.android.ram.core.data.database.model.MaterialEntity

@Dao
internal interface MaterialDao {

    @Query("SELECT * FROM materials")
    suspend fun getAll(): List<MaterialEntity>

    @Query("SELECT * FROM materials WHERE :materialUid == uid")
    suspend fun getByUid(materialUid: String): MaterialEntity?

    @Query("DELETE FROM materials")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(materialEntity: MaterialEntity)
}
