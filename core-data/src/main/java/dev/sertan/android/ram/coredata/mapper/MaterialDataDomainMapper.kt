/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.mapper

import dev.sertan.android.ram.corecommon.model.MaterialDto
import dev.sertan.android.ram.coredata.database.model.MaterialEntity
import dev.sertan.android.ram.coredata.service.model.NetworkMaterial

internal fun MaterialEntity.toDomainModel(): MaterialDto = MaterialDto(
    uid = materialUid,
    description = description,
    mediaUrl = mediaUrl,
    attribution = attribution
)

internal fun List<MaterialEntity>.toDomainModel(): List<MaterialDto> =
    map(MaterialEntity::toDomainModel)

internal fun MaterialDto.toEntityModel(): MaterialEntity = MaterialEntity(
    materialUid = uid,
    description = description,
    mediaUrl = mediaUrl,
    attribution = attribution
)

internal fun MaterialDto.toNetworkModel(): NetworkMaterial = NetworkMaterial(
    materialUid = uid,
    description = description,
    mediaUrl = mediaUrl,
    attribution = attribution
)

internal fun Array<out MaterialDto>.toEntityModel(): Array<MaterialEntity> =
    map(MaterialDto::toEntityModel).toTypedArray()

internal fun Array<out MaterialDto>.toNetworkModel(): Array<NetworkMaterial> =
    map(MaterialDto::toNetworkModel).toTypedArray()

internal fun NetworkMaterial.toDomainModel(): MaterialDto = MaterialDto(
    uid = materialUid,
    description = description,
    mediaUrl = mediaUrl,
    attribution = attribution
)

@JvmName("materialResponseListToDomainModel")
internal fun List<NetworkMaterial>.toDomainModel(): List<MaterialDto> =
    map(NetworkMaterial::toDomainModel)
