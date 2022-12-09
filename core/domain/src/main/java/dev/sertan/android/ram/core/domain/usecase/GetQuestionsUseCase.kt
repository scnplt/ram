/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.domain.usecase

import dev.sertan.android.ram.core.data.repository.MaterialRepository
import dev.sertan.android.ram.core.data.repository.QuestionRepository
import dev.sertan.android.ram.core.domain.model.toUIModel
import dev.sertan.android.ram.core.model.domain.QuestionDto
import dev.sertan.android.ram.core.model.ui.Material
import dev.sertan.android.ram.core.model.ui.Question
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val materialRepository: MaterialRepository
) {

    suspend operator fun invoke(): List<Question> = questionRepository.getQuestions().getOrNull()
        ?.map { it.toUIModel(materials = getQuestionMaterials(it)) }.orEmpty()

    private suspend fun getQuestionMaterials(question: QuestionDto): List<Material> =
        question.materialUidList.mapNotNull {
            materialRepository.getMaterial(materialUid = it).getOrNull()?.toUIModel()
        }
}
