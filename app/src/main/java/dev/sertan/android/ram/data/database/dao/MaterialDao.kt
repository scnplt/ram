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
import dev.sertan.android.ram.data.datasource.MaterialDataSource
import dev.sertan.android.ram.data.model.MaterialEntity

@Dao
internal interface MaterialDao : MaterialDataSource {

    @Query("SELECT * FROM materials WHERE :lessonId == ownerLessonId")
    override suspend fun getLessonMaterials(lessonId: Long): List<MaterialEntity>

    @Query("SELECT * FROM materials WHERE :questionId == questionId")
    override suspend fun getQuestionMaterials(questionId: Long): List<MaterialEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun createMaterial(vararg materialEntity: MaterialEntity)

    @Delete
    override suspend fun deleteMaterial(vararg materialEntity: MaterialEntity)
}
