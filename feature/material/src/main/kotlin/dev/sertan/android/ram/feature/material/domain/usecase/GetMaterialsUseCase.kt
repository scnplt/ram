/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.material.domain.usecase

import dev.sertan.android.ram.feature.material.domain.mapper.MaterialMapper
import dev.sertan.android.ram.feature.material.domain.repository.MaterialRepository
import dev.sertan.android.ram.feature.material.ui.Material
import javax.inject.Inject

class GetMaterialsUseCase @Inject internal constructor(
    private val materialRepository: MaterialRepository,
    private val materialMapper: MaterialMapper
) {

    suspend operator fun invoke(shuffle: Boolean? = DEFAULT_SHUFFLE): List<Material> =
        with(materialRepository) {
            val dtoList = getMaterials().getOrNull()?.takeUnless { it.isEmpty() }
                ?: getMaterials(update = true).getOrNull()
            dtoList?.map(materialMapper::toUIModel).orEmpty()
                .let { if (shuffle == false) it else it.shuffled() }
        }

    suspend fun getMaterial(materialUid: String): Material? =
        materialRepository.getMaterial(materialUid).getOrNull()
            ?.let(materialMapper::toUIModel)

    companion object {
        const val DEFAULT_SHUFFLE = true
    }
}
