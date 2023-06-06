/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.domain.usecase

import dev.sertan.android.ram.appmemory.data.repository.GapFillingQuestionRepository
import dev.sertan.android.ram.appmemory.domain.mapper.GapFillingQuestionMapper
import dev.sertan.android.ram.appmemory.ui.model.GapFillingQuestion
import dev.sertan.android.ram.feature.material.domain.usecase.GetMaterialsUseCase
import javax.inject.Inject

internal class GetGapFillingQuestionsUseCase @Inject constructor(
    private val repository: GapFillingQuestionRepository,
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val mapper: GapFillingQuestionMapper
) {

    suspend operator fun invoke(): List<GapFillingQuestion> = with(repository) {
        val questionDtoList = getQuestions().getOrNull()?.takeUnless { it.isEmpty() }
            ?: getQuestions(update = true).getOrNull()
        questionDtoList?.mapNotNull { questionDto ->
            val materials = questionDto.materialUidList.mapNotNull { materialUid ->
                getMaterialsUseCase.getMaterial(materialUid)
            }
            getMaterialsUseCase.getMaterial(questionDto.contentMaterialUid)?.let {
                mapper.toUIModel(dto = questionDto, materials = materials, contentMaterial = it)
            }
        }.orEmpty().shuffled()
    }
}
