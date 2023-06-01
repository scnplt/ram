/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.material.data.repository

import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapNotNull
import dev.sertan.android.ram.core.data.FirestoreDataReceiver
import dev.sertan.android.ram.feature.material.data.datasource.local.MaterialDao
import dev.sertan.android.ram.feature.material.data.datasource.local.MaterialEntity
import dev.sertan.android.ram.feature.material.data.datasource.remote.NetworkMaterial
import dev.sertan.android.ram.feature.material.data.mapper.toDto
import dev.sertan.android.ram.feature.material.data.mapper.toEntity
import dev.sertan.android.ram.feature.material.domain.model.MaterialDto
import javax.inject.Inject

internal class MaterialRepository @Inject constructor(
    private val dataReceiver: FirestoreDataReceiver<NetworkMaterial>,
    private val dao: MaterialDao,
    private val logger: RamLogger
) {

    suspend fun getMaterials(update: Boolean = false): Result<List<MaterialDto>> =
        tryGetWithResult(logger) {
            if (update) updateMaterialsFromRemote()
            dao.getAll().map(MaterialEntity::toDto)
        }

    suspend fun getMaterial(materialUid: String): Result<MaterialDto?> =
        tryGetWithResult(logger) { dao.getByUid(materialUid)?.toDto() }

    private suspend fun updateMaterialsFromRemote() {
        val remoteData = dataReceiver.getAll()
        dao.deleteAll()
        remoteData.tryMapNotNull(logger = logger, transform = NetworkMaterial::toEntity)
            .forEach { dao.insert(materialEntity = it) }
    }
}
