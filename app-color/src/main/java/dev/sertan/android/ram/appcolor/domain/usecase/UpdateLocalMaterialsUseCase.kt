/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.domain.usecase

import dev.sertan.android.ram.appcolor.domain.repository.MaterialRepository
import javax.inject.Inject

class UpdateLocalMaterialsUseCase @Inject constructor(
    private val materialRepository: MaterialRepository
) {

    suspend operator fun invoke(): Boolean = with(materialRepository) {
        getMaterialsFromRemote().getOrNull()
            ?.let { saveMaterialToLocal(materialArray = it.toTypedArray()).isSuccess } ?: false
    }
}
