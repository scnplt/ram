/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.domain.usecase

import dev.sertan.android.ram.appmemory.domain.mapper.MatchingQuestionMapper
import dev.sertan.android.ram.appmemory.domain.repository.MatchingQuestionRepository
import dev.sertan.android.ram.appmemory.ui.model.MatchingQuestion
import dev.sertan.android.ram.feature.material.domain.usecase.GetMaterialsUseCase
import javax.inject.Inject

internal class MatchingQuestionsUseCase @Inject constructor(
    private val repository: MatchingQuestionRepository,
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val mapper: MatchingQuestionMapper
) {

    suspend operator fun invoke(): List<MatchingQuestion> = with(repository) {
        val questionDtoList = getQuestions().getOrNull()?.takeUnless { it.isEmpty() }
            ?: getQuestions(update = true).getOrNull()
        questionDtoList?.map { questionDto ->
            val materials = questionDto.materialUidList
                .mapNotNull { materialUid -> getMaterialsUseCase.getMaterial(materialUid) }
            mapper.toUIModel(
                dto = questionDto,
                materials = materials
            )
        }.orEmpty().shuffled()
    }
}
