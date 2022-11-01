/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredomain.mapper

import dev.sertan.android.ram.corecommon.model.MaterialDto
import dev.sertan.android.ram.coreui.model.Material

fun MaterialDto.toUiModel(): Material = Material(
    id = id,
    description = description,
    mediaUrl = mediaUrl,
    attribution = attribution
)

fun Material.toDomainModel(): MaterialDto = MaterialDto(
    id = id,
    description = description,
    mediaUrl = mediaUrl,
    attribution = attribution
)

fun List<MaterialDto>.toUiModel(): List<Material> = map(MaterialDto::toUiModel)

fun List<Material>.toDomainModel(): Array<out MaterialDto> =
    map(Material::toDomainModel).toTypedArray()
