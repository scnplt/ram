/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training.domain.mapper

import dev.sertan.android.ram.feature.material.ui.Material
import dev.sertan.android.ram.feature.training.domain.model.QuestionDto
import dev.sertan.android.ram.feature.training.ui.model.Question
import java.util.Locale
import javax.inject.Inject

internal class QuestionMapper @Inject constructor(private val locale: Locale) {

    fun toUIModel(dto: QuestionDto, materials: List<Material>): Question = Question(
        uid = dto.uid,
        content = dto.content.replaceFirstChar { it.titlecase(locale) },
        materials = materials,
        correctMaterialUid = dto.correctMaterialUid
    )
}
