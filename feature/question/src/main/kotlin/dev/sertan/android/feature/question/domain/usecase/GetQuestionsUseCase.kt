/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.feature.question.domain.usecase

import dev.sertan.android.feature.question.domain.mapper.toUIModel
import dev.sertan.android.feature.question.domain.model.QuestionDto
import dev.sertan.android.feature.question.domain.repository.QuestionRepository
import dev.sertan.android.ram.core.model.ui.Material
import dev.sertan.android.ram.core.model.ui.Question
import dev.sertan.android.ram.feature.training.domain.mapper.toUIModel
import dev.sertan.android.ram.feature.training.domain.repository.MaterialRepository
import javax.inject.Inject

internal class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val materialRepository: MaterialRepository
) {

    suspend operator fun invoke(): List<Question> = with(questionRepository) {
        val dtoList = getQuestions().getOrNull()?.takeUnless { it.isEmpty() }
            ?: getQuestions(update = true).getOrNull()
        dtoList?.map { it.toUIModel(materials = getQuestionMaterials(question = it)) }.orEmpty()
    }

    private suspend fun getQuestionMaterials(question: QuestionDto): List<Material> =
        question.materialUidList.mapNotNull {
            materialRepository.getMaterial(materialUid = it).getOrNull()?.toUIModel()
        }
}
