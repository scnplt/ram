/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.domain.model

import dev.sertan.android.ram.core.model.domain.QuestionDto
import dev.sertan.android.ram.core.model.ui.Material
import dev.sertan.android.ram.core.model.ui.Question

internal fun QuestionDto.toUIModel(materials: List<Material>): Question = Question(
    uid = uid,
    content = content.replaceFirstChar { it.uppercase() },
    materials = materials,
    correctMaterialUid = correctMaterialUid
)
