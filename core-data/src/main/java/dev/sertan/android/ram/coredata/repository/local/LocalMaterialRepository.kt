/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.repository.local

import dev.sertan.android.ram.corecommon.model.MaterialDto
import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import dev.sertan.android.ram.coredata.database.dao.MaterialDao
import dev.sertan.android.ram.coredata.mapper.toDomainModel
import dev.sertan.android.ram.coredata.mapper.toEntityModel

internal class LocalMaterialRepository(private val materialDao: MaterialDao) : MaterialRepository {

    override suspend fun getAllMaterials(): Result<List<MaterialDto>> =
        runCatching { materialDao.getAll().toDomainModel() }

    override suspend fun getMaterialByUid(materialUid: String): Result<MaterialDto?> =
        runCatching { materialDao.getByUid(materialUid).toDomainModel() }

    override suspend fun saveMaterial(vararg materialArray: MaterialDto): Result<Unit> =
        runCatching { materialDao.insert(materialEntityArray = materialArray.toEntityModel()) }

    override suspend fun deleteMaterial(vararg materialArray: MaterialDto): Result<Unit> =
        runCatching { materialDao.delete(materialEntityArray = materialArray.toEntityModel()) }

    override suspend fun updateMaterial(material: MaterialDto): Result<Unit> =
        runCatching { materialDao.update(materialEntity = material.toEntityModel()) }
}
