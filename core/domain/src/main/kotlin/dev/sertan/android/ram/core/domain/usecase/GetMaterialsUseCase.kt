/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.domain.usecase

import dev.sertan.android.ram.core.data.repository.MaterialRepository
import dev.sertan.android.ram.core.domain.model.toUIModel
import dev.sertan.android.ram.core.model.ui.Material
import javax.inject.Inject

class GetMaterialsUseCase @Inject constructor(private val materialRepository: MaterialRepository) {

    suspend operator fun invoke(): List<Material> =
        materialRepository.getMaterials().getOrNull()?.map { it.toUIModel() }.orEmpty()
}
