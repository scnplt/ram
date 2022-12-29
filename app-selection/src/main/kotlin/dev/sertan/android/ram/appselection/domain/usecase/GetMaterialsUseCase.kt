/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.domain.usecase

import dev.sertan.android.ram.appselection.domain.mapper.toUIModel
import dev.sertan.android.ram.appselection.domain.model.MaterialDto
import dev.sertan.android.ram.appselection.domain.repository.MaterialRepository
import dev.sertan.android.ram.appselection.ui.model.Material
import javax.inject.Inject

internal class GetMaterialsUseCase @Inject constructor(
    private val materialRepository: MaterialRepository
) {

    suspend operator fun invoke(): List<Material> = with(materialRepository) {
        val dtoList = getMaterials().getOrNull()?.takeUnless { it.isEmpty() }
            ?: getMaterials(update = true).getOrNull()
        dtoList?.map(MaterialDto::toUIModel)?.shuffled().orEmpty()
    }
}
