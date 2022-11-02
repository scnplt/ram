/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.repository.remote

import dev.sertan.android.ram.corecommon.model.MaterialDto
import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import dev.sertan.android.ram.coredata.mapper.toDomainModel
import dev.sertan.android.ram.coredata.mapper.toNetworkModel
import dev.sertan.android.ram.coredata.service.MaterialService

internal class RemoteMaterialRepository(
    private val materialService: MaterialService
) : MaterialRepository {

    override suspend fun getAllMaterials(): Result<List<MaterialDto>> =
        runCatching { materialService.getAllMaterials().toDomainModel() }

    override suspend fun getMaterialByUid(materialUid: String): Result<MaterialDto?> =
        runCatching { materialService.getMaterialById(materialUid)?.toDomainModel() }

    override suspend fun saveMaterial(vararg materialArray: MaterialDto): Result<Unit> =
        runCatching {
            materialService.saveMaterial(materialArray = materialArray.toNetworkModel())
        }

    override suspend fun deleteMaterial(vararg materialArray: MaterialDto): Result<Unit> =
        runCatching {
            materialService.deleteMaterial(materialArray = materialArray.toNetworkModel())
        }

    override suspend fun updateMaterial(material: MaterialDto): Result<Unit> =
        runCatching { materialService.updateMaterial(material = material.toNetworkModel()) }
}
