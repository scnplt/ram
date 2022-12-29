/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.domain.usecase

import dev.sertan.android.ram.appnumber.domain.mapper.toUIModel
import dev.sertan.android.ram.appnumber.domain.model.SectionDto
import dev.sertan.android.ram.appnumber.domain.repository.SectionRepository
import dev.sertan.android.ram.appnumber.ui.model.Section
import javax.inject.Inject

internal class GetSectionsUseCase @Inject constructor(
    private val sectionRepository: SectionRepository
) {

    suspend operator fun invoke(): List<Section> = with(sectionRepository) {
        val dtoList = getSections().getOrNull()?.takeUnless { it.isEmpty() }
            ?: getSections(update = true).getOrNull()
        dtoList?.map(SectionDto::toUIModel).orEmpty()
    }
}
