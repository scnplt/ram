/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.domain.mapper

import dev.sertan.android.ram.appselection.domain.model.QuestionDto
import dev.sertan.android.ram.appselection.ui.model.Material
import dev.sertan.android.ram.appselection.ui.model.Question

internal fun QuestionDto.toUIModel(materials: List<Material>): Question = Question(
    uid = uid,
    content = content.replaceFirstChar { it.uppercase() },
    materials = materials,
    correctMaterialUid = correctMaterialUid
)