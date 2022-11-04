/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredomain.usecase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import dev.sertan.android.ram.coredomain.mapper.toUiModel
import dev.sertan.android.ram.coredomain.worker.UpdateLocalMaterialsWorker
import dev.sertan.android.ram.coreui.model.Material
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class GetMaterialsUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val materialRepository: MaterialRepository,
) {

    suspend operator fun invoke(): List<Material> = withContext(Dispatchers.IO) {
        materialRepository.getMaterialsFromLocal().getOrNull()?.shuffled()?.toUiModel()
            .takeUnless { it.isNullOrEmpty() }
            ?: run {
                UpdateLocalMaterialsWorker.uniqueStart(context)
                emptyList()
            }
    }
}
