/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.question.domain.usecase

import dev.sertan.android.ram.feature.question.domain.mapper.QuestionMapper
import dev.sertan.android.ram.feature.question.domain.repository.MaterialRepository
import dev.sertan.android.ram.feature.question.domain.repository.QuestionRepository
import dev.sertan.android.ram.feature.question.ui.model.Question
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val materialRepository: MaterialRepository,
    private val questionMapper: QuestionMapper
) {

    suspend operator fun invoke(): List<Question> = with(questionRepository) {
        materialRepository.updateMaterialsFromRemote()
        val questionDtoList = getQuestions().getOrNull()?.takeUnless { it.isEmpty() }
            ?: getQuestions(update = true).getOrNull()
        questionDtoList?.map { questionDto ->
            val materialDtoList = questionDto.materialUidList
                .mapNotNull { materialRepository.getMaterial(materialUid = it).getOrNull() }
            questionMapper.toUIModel(dto = questionDto, materials = materialDtoList)
        }?.shuffled().orEmpty()
    }
}
