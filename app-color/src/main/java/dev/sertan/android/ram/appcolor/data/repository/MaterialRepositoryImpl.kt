/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.data.repository

import dev.sertan.android.ram.appcolor.data.database.dao.MaterialDao
import dev.sertan.android.ram.appcolor.data.mapper.toDomainModel
import dev.sertan.android.ram.appcolor.data.mapper.toEntityModel
import dev.sertan.android.ram.appcolor.data.service.MaterialService
import dev.sertan.android.ram.appcolor.domain.model.MaterialDto
import dev.sertan.android.ram.appcolor.domain.repository.MaterialRepository
import dev.sertan.android.ram.core.data.service.MediaService
import dev.sertan.android.ram.core.util.tryGetResultWithLog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MaterialRepositoryImpl @Inject constructor(
    private val materialDao: MaterialDao,
    private val materialService: MaterialService,
    private val mediaService: MediaService
) : MaterialRepository {

    override suspend fun getMaterialsFromLocal(): Result<List<MaterialDto>> =
        tryGetResultWithLog { materialDao.getAll().toDomainModel() }

    override suspend fun getMaterialsFromRemote(): Result<List<MaterialDto>> =
        tryGetResultWithLog { materialService.getAllMaterials().toDomainModel(mediaService) }

    override suspend fun getMaterialFromLocalByUid(uid: String): Result<MaterialDto?> =
        tryGetResultWithLog { materialDao.getByUid(uid).toDomainModel() }

    override suspend fun getMaterialFromRemoteByUid(uid: String): Result<MaterialDto?> =
        tryGetResultWithLog { materialService.getMaterialByUid(uid)?.toDomainModel(mediaService) }

    override suspend fun saveMaterialToLocal(vararg materialArray: MaterialDto): Result<Unit> =
        tryGetResultWithLog {
            materialDao.insert(materialEntityArray = materialArray.toEntityModel())
        }

    override suspend fun deleteMaterialFromLocal(vararg materialArray: MaterialDto): Result<Unit> =
        tryGetResultWithLog {
            materialDao.delete(materialEntityArray = materialArray.toEntityModel())
        }

    override suspend fun updateLocalMaterial(material: MaterialDto): Result<Unit> =
        tryGetResultWithLog { materialDao.update(materialEntity = material.toEntityModel()) }
}
