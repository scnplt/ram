/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.repository

import dev.sertan.android.ram.corecommon.model.MaterialDto
import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import dev.sertan.android.ram.coredata.database.dao.MaterialDao
import dev.sertan.android.ram.coredata.mapper.toDomainModel
import dev.sertan.android.ram.coredata.mapper.toEntityModel
import dev.sertan.android.ram.coredata.service.MaterialService
import dev.sertan.android.ram.coredata.service.MediaService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MaterialRepositoryImpl @Inject constructor(
    private val materialDao: MaterialDao,
    private val materialService: MaterialService,
    private val mediaService: MediaService
) : MaterialRepository {

    override suspend fun getMaterialsFromLocal(): Result<List<MaterialDto>> =
        runCatching { materialDao.getAll().toDomainModel() }

    override suspend fun getMaterialsFromRemote(): Result<List<MaterialDto>> =
        runCatching { materialService.getAllMaterials().toDomainModel(mediaService) }

    override suspend fun getMaterialFromLocalByUid(uid: String): Result<MaterialDto?> =
        runCatching { materialDao.getByUid(uid).toDomainModel() }

    override suspend fun getMaterialFromRemoteByUid(uid: String): Result<MaterialDto?> =
        runCatching { materialService.getMaterialByUid(uid)?.toDomainModel(mediaService) }

    override suspend fun saveMaterialToLocal(vararg materialArray: MaterialDto): Result<Unit> =
        runCatching { materialDao.insert(materialEntityArray = materialArray.toEntityModel()) }

    override suspend fun deleteMaterialFromLocal(vararg materialArray: MaterialDto): Result<Unit> =
        runCatching { materialDao.delete(materialEntityArray = materialArray.toEntityModel()) }

    override suspend fun updateLocalMaterial(material: MaterialDto): Result<Unit> =
        runCatching { materialDao.update(materialEntity = material.toEntityModel()) }
}
