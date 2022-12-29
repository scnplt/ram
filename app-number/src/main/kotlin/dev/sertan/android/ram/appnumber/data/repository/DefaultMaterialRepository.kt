/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.data.repository

import dev.sertan.android.ram.appnumber.data.database.dao.MaterialDao
import dev.sertan.android.ram.appnumber.data.database.model.MaterialEntity
import dev.sertan.android.ram.appnumber.data.mapper.toDto
import dev.sertan.android.ram.appnumber.data.mapper.toEntity
import dev.sertan.android.ram.appnumber.data.service.MaterialService
import dev.sertan.android.ram.appnumber.data.service.model.NetworkMaterial
import dev.sertan.android.ram.appnumber.domain.model.MaterialDto
import dev.sertan.android.ram.appnumber.domain.repository.MaterialRepository
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapNotNull
import dev.sertan.android.ram.core.common.tryWithLogger
import javax.inject.Inject

internal class DefaultMaterialRepository @Inject constructor(
    private val materialDao: MaterialDao,
    private val materialService: MaterialService,
    private val logger: RamLogger
) : MaterialRepository {

    override suspend fun getMaterials(update: Boolean): Result<List<MaterialDto>> =
        tryGetWithResult(logger) {
            if (update) updateMaterialsFromRemote()
            materialDao.getAll().map(MaterialEntity::toDto)
        }

    override suspend fun getMaterial(materialUid: String): Result<MaterialDto?> =
        tryGetWithResult(logger) { materialDao.getByUid(materialUid)?.toDto() }

    override suspend fun saveMaterialToLocal(material: MaterialDto): Boolean =
        tryWithLogger(logger) { materialDao.insert(materialEntity = material.toEntity()) }

    override suspend fun refreshMaterials(): Boolean =
        tryWithLogger(logger) { updateMaterialsFromRemote() }

    private suspend fun updateMaterialsFromRemote() {
        val remoteData = materialService.getMaterials()
        materialDao.deleteAll()
        remoteData.tryMapNotNull(logger = logger, transform = NetworkMaterial::toEntity)
            .forEach { materialDao.insert(materialEntity = it) }
    }
}
