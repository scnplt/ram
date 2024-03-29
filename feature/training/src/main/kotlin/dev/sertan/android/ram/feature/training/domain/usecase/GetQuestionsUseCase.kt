/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training.domain.usecase

import dev.sertan.android.ram.feature.material.domain.usecase.GetMaterialsUseCase
import dev.sertan.android.ram.feature.training.data.repository.QuestionRepository
import dev.sertan.android.ram.feature.training.domain.mapper.QuestionMapper
import dev.sertan.android.ram.feature.training.ui.model.Question
import javax.inject.Inject

class GetQuestionsUseCase @Inject internal constructor(
    private val questionRepository: QuestionRepository,
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val questionMapper: QuestionMapper
) {

    suspend operator fun invoke(shuffle: Boolean? = DEFAULT_SHUFFLE): List<Question> =
        with(questionRepository) {
            val questionDtoList = getQuestions().getOrNull()?.takeUnless { it.isEmpty() }
                ?: getQuestions(update = true).getOrNull()
            questionDtoList?.map { questionDto ->
                val materials = questionDto.materialUidList.mapNotNull { materialUid ->
                    getMaterialsUseCase.getMaterial(materialUid)
                }
                questionMapper.toUIModel(dto = questionDto, materials = materials)
            }
        }.orEmpty().let { if (shuffle == false) it else it.shuffled() }

    companion object {
        const val DEFAULT_SHUFFLE = true
    }
}
