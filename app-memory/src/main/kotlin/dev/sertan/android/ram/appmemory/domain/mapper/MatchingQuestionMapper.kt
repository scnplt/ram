/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.domain.mapper

import dev.sertan.android.ram.appmemory.domain.model.MatchingQuestionDto
import dev.sertan.android.ram.appmemory.ui.model.MatchingQuestion
import dev.sertan.android.ram.feature.material.ui.Material
import java.util.Locale
import javax.inject.Inject

internal class MatchingQuestionMapper @Inject constructor(private val locale: Locale) {

    fun toUIModel(
        dto: MatchingQuestionDto,
        materials: List<Material>
    ): MatchingQuestion = MatchingQuestion(
        uid = dto.uid,
        content = dto.content.replaceFirstChar { it.titlecase(locale) },
        materials = materials
    )
}
