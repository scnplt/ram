/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training.domain.mapper

import dev.sertan.android.ram.feature.training.domain.model.MaterialDto
import dev.sertan.android.ram.feature.training.ui.model.Material
import java.util.Locale
import javax.inject.Inject

internal class MaterialMapper @Inject constructor(private val locale: Locale) {

    fun toUIModel(dto: MaterialDto): Material = Material(
        uid = dto.uid,
        description = dto.description.replaceFirstChar { it.titlecase(locale) },
        mediaUrl = dto.mediaUrl,
        attribution = dto.attribution
    )
}
