/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.model.mapper

import dev.sertan.android.ram.coredomain.model.MaterialDto
import dev.sertan.android.ram.data.model.MaterialEntity

internal fun MaterialEntity.toDomainModel(): MaterialDto {
    return MaterialDto(
        id = materialId,
        description = description,
        mediaUrl = mediaUrl,
        attribution = attribution
    )
}

internal fun List<MaterialEntity>.toDomainModel(): List<MaterialDto> {
    return map(MaterialEntity::toDomainModel)
}

internal fun MaterialDto.toDataModel(): MaterialEntity {
    return MaterialEntity(
        materialId = id,
        description = description,
        mediaUrl = mediaUrl,
        attribution = attribution
    )
}

internal fun Array<out MaterialDto>.toDataModel(): Array<MaterialEntity> {
    return map(MaterialDto::toDataModel).toTypedArray()
}
