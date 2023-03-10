/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.question.domain.mapper

import dev.sertan.android.ram.feature.question.domain.model.MaterialDto
import dev.sertan.android.ram.feature.question.domain.model.QuestionDto
import dev.sertan.android.ram.feature.question.ui.model.Question
import java.util.Locale
import javax.inject.Inject

class QuestionMapper @Inject constructor(
    private val locale: Locale,
    private val materialMapper: MaterialMapper
) {

    fun toUIModel(dto: QuestionDto, materials: List<MaterialDto>): Question = Question(
        uid = dto.uid,
        content = dto.content.replaceFirstChar { it.titlecase(locale) },
        materials = materials.map(materialMapper::toUIModel),
        correctMaterialUid = dto.correctMaterialUid
    )
}
