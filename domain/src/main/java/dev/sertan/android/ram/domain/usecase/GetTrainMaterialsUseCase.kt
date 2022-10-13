/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.domain.usecase

import dev.sertan.android.ram.corecommon.di.LocalDataSource
import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import dev.sertan.android.ram.coreui.model.Material
import dev.sertan.android.ram.domain.mapper.toUiModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTrainMaterialsUseCase @Inject constructor(
    @LocalDataSource private val materialRepository: MaterialRepository
) {
    suspend operator fun invoke(): Result<List<Material>> = withContext(Dispatchers.IO) {
        materialRepository.getAllMaterials().mapCatching { materialDtoList ->
            materialDtoList.toUiModel().shuffled()
        }
    }
}
