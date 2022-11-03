/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.mapper

import android.net.Uri
import dev.sertan.android.ram.corecommon.model.MaterialDto
import dev.sertan.android.ram.coredata.database.model.MaterialEntity
import dev.sertan.android.ram.coredata.service.MediaService
import dev.sertan.android.ram.coredata.service.model.NetworkMaterial

internal fun MaterialEntity.toDomainModel(): MaterialDto = MaterialDto(
    uid = materialUid,
    description = description,
    mediaUri = Uri.parse(mediaUriString),
    attribution = attribution
)

internal fun List<MaterialEntity>.toDomainModel(): List<MaterialDto> =
    map(MaterialEntity::toDomainModel)

internal fun MaterialDto.toEntityModel(): MaterialEntity = MaterialEntity(
    materialUid = uid,
    description = description,
    mediaUriString = mediaUri.toString(),
    attribution = attribution
)

internal fun Array<out MaterialDto>.toEntityModel(): Array<MaterialEntity> =
    map(MaterialDto::toEntityModel).toTypedArray()

internal suspend fun NetworkMaterial.toDomainModel(mediaService: MediaService): MaterialDto? =
    materialUid?.let {
        MaterialDto(
            uid = it,
            description = description.orEmpty(),
            mediaUri = mediaService.getMediaDownloadUri(mediaPath),
            attribution = attribution
        )
    }

internal suspend fun List<NetworkMaterial>.toDomainModel(
    mediaService: MediaService
): List<MaterialDto> = mapNotNull { it.toDomainModel(mediaService) }
