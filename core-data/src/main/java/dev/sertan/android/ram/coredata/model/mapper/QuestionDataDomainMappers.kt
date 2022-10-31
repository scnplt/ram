/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.model.mapper

import dev.sertan.android.ram.corecommon.model.QuestionDto
import dev.sertan.android.ram.coredata.model.QuestionEntity

internal fun QuestionEntity.toDomainModel(): QuestionDto {
    return QuestionDto(
        id = questionId,
        content = content,
        answerMaterialId = answerMaterialId
    )
}

internal fun List<QuestionEntity>.toDomainModel(): List<QuestionDto> {
    return map(QuestionEntity::toDomainModel)
}

internal fun QuestionDto.toDataModel(): QuestionEntity {
    return QuestionEntity(
        questionId = id,
        content = content,
        answerMaterialId = answerMaterialId
    )
}

internal fun Array<out QuestionDto>.toDataModel(): Array<QuestionEntity> {
    return map(QuestionDto::toDataModel).toTypedArray()
}
