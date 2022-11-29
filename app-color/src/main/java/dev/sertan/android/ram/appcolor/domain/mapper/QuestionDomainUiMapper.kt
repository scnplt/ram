/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.domain.mapper

import dev.sertan.android.ram.appcolor.domain.model.QuestionDto
import dev.sertan.android.ram.appcolor.ui.model.Material
import dev.sertan.android.ram.appcolor.ui.model.Question

internal fun QuestionDto.toUiModel(materials: List<Material>): Question = Question(
    uid = uid,
    content = content.replaceFirstChar { it.titlecase() },
    materials = materials,
    correctMaterialId = answerMaterialUid
)
