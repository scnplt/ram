/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.model

import dev.sertan.android.ram.core.data.database.model.QuestionEntity
import dev.sertan.android.ram.core.data.service.model.NetworkQuestion
import dev.sertan.android.ram.core.model.domain.QuestionDto

internal fun QuestionEntity.toDto(): QuestionDto = QuestionDto(
    uid = uid,
    content = content,
    materialUidList = materialUidList.shuffled(),
    correctMaterialUid = correctMaterialUid
)

internal fun QuestionDto.toEntity(): QuestionEntity = QuestionEntity(
    uid = uid,
    content = content,
    correctMaterialUid = correctMaterialUid,
    materialUidList = materialUidList
)

internal fun NetworkQuestion.toEntity(): QuestionEntity = QuestionEntity(
    uid = uid!!,
    content = content!!,
    correctMaterialUid = correctAnswerUid!!,
    materialUidList = materialUidList!!
)
