/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.question.domain.usecase

import dev.sertan.android.ram.feature.question.domain.mapper.toUIModel
import dev.sertan.android.ram.feature.question.domain.model.QuestionDto
import dev.sertan.android.ram.feature.question.domain.repository.MaterialRepository
import dev.sertan.android.ram.feature.question.domain.repository.QuestionRepository
import dev.sertan.android.ram.feature.question.ui.model.Material
import dev.sertan.android.ram.feature.question.ui.model.Question
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val materialRepository: MaterialRepository
) {

    suspend operator fun invoke(): List<Question> = with(questionRepository) {
        materialRepository.updateMaterialsFromRemote()
        val dtoList = getQuestions().getOrNull()?.takeUnless { it.isEmpty() }
            ?: getQuestions(update = true).getOrNull()
        dtoList?.map { it.toUIModel(materials = getQuestionMaterials(question = it)) }
            ?.shuffled().orEmpty()
    }

    private suspend fun getQuestionMaterials(question: QuestionDto): List<Material> =
        question.materialUidList.mapNotNull {
            materialRepository.getMaterial(materialUid = it).getOrNull()?.toUIModel()
        }
}
