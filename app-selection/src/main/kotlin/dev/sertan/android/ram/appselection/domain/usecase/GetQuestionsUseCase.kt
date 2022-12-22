/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.domain.usecase

import dev.sertan.android.ram.appselection.domain.mapper.toUIModel
import dev.sertan.android.ram.appselection.domain.model.QuestionDto
import dev.sertan.android.ram.appselection.domain.repository.MaterialRepository
import dev.sertan.android.ram.appselection.domain.repository.QuestionRepository
import dev.sertan.android.ram.appselection.ui.model.Material
import dev.sertan.android.ram.appselection.ui.model.Question
import javax.inject.Inject

internal class GetQuestionsUseCase @Inject constructor(
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
