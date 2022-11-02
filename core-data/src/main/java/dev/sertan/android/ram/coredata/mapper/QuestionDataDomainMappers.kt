/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.mapper

import dev.sertan.android.ram.corecommon.model.QuestionDto
import dev.sertan.android.ram.coredata.database.model.QuestionEntity

internal fun QuestionEntity.toDomainModel(): QuestionDto = QuestionDto(
    uid = questionUid,
    content = content,
    answerMaterialId = answerMaterialId
)

internal fun List<QuestionEntity>.toDomainModel(): List<QuestionDto> =
    map(QuestionEntity::toDomainModel)

internal fun QuestionDto.toEntityModel(): QuestionEntity = QuestionEntity(
    questionUid = uid,
    content = content,
    answerMaterialId = answerMaterialId
)

internal fun Array<out QuestionDto>.toEntityModel(): Array<QuestionEntity> =
    map(QuestionDto::toEntityModel).toTypedArray()
