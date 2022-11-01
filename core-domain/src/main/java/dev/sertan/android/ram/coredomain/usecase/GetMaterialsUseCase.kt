/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredomain.usecase

import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import dev.sertan.android.ram.coredomain.mapper.toUiModel
import dev.sertan.android.ram.coreui.model.Material
import javax.inject.Inject

class GetMaterialsUseCase @Inject constructor(private val materialRepository: MaterialRepository) {

    suspend operator fun invoke(): List<Material> = materialRepository.getAllMaterials()
        .getOrNull()?.toUiModel().orEmpty()
}
