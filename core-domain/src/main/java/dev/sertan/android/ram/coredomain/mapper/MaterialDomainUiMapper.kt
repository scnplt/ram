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

internal fun MaterialDto.toUiModel(): Material = Material(
    uid = uid,
    description = description.replaceFirstChar { it.titlecase() },
    mediaUrl = mediaUrl,
    attribution = attribution
)

internal fun List<MaterialDto>.toUiModel(): List<Material> = map(MaterialDto::toUiModel)
