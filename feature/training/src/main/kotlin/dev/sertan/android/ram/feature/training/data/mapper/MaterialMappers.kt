/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training.data.mapper

import dev.sertan.android.ram.feature.training.data.database.model.MaterialEntity
import dev.sertan.android.ram.feature.training.data.service.model.NetworkMaterial
import dev.sertan.android.ram.feature.training.domain.model.MaterialDto

internal fun MaterialEntity.toDto(): MaterialDto = MaterialDto(
    uid = uid,
    description = description,
    mediaUrl = mediaUrl,
    attribution = attribution
)

internal fun MaterialDto.toEntity(): MaterialEntity = MaterialEntity(
    uid = uid,
    description = description,
    mediaUrl = mediaUrl,
    attribution = attribution
)

internal fun NetworkMaterial.toEntity(): MaterialEntity = MaterialEntity(
    uid = uid!!,
    description = description!!,
    mediaUrl = mediaUrl!!,
    attribution = attribution
)
