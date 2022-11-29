/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.domain.usecase

import dev.sertan.android.ram.appcolor.domain.mapper.toUiModel
import dev.sertan.android.ram.appcolor.domain.repository.MaterialRepository
import dev.sertan.android.ram.appcolor.ui.model.Material
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class GetMaterialsUseCase @Inject constructor(
    private val materialRepository: MaterialRepository,
    private val updateLocalMaterialsUseCase: UpdateLocalMaterialsUseCase
) {

    suspend operator fun invoke(): List<Material> = withContext(Dispatchers.IO) {
        materialRepository.run {
            getMaterialsFromLocal().getOrNull() ?: getMaterialsFromRemote().getOrNull()
                .also { updateLocalMaterialsUseCase() }
        }?.toUiModel()?.shuffled().orEmpty()
    }

    suspend fun getByUid(uid: String): Material? = withContext(Dispatchers.IO) {
        materialRepository.getMaterialFromLocalByUid(uid = uid).getOrElse {
            updateLocalMaterialsUseCase()
            null
        }?.toUiModel()
    }
}
