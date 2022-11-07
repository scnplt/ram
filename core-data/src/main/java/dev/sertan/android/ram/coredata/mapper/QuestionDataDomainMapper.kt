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
import dev.sertan.android.ram.coredata.service.model.NetworkQuestion

internal fun QuestionEntity.toDomainModel(): QuestionDto = QuestionDto(
    uid = questionUid,
    content = content,
    answerMaterialUid = answerMaterialUid,
    materialUidList = materialUidList
)

internal fun List<QuestionEntity>.toDomainModel(): List<QuestionDto> =
    map(QuestionEntity::toDomainModel)

internal fun NetworkQuestion.toDomainModel(): QuestionDto? = questionUid?.let {
    QuestionDto(
        uid = it,
        content = content.orEmpty(),
        answerMaterialUid = answerMaterialUid.orEmpty(),
        materialUidList = materialUidList
    )
}

internal fun QuestionDto.toDataModel(): QuestionEntity = QuestionEntity(
    questionUid = uid,
    content = content,
    answerMaterialUid = answerMaterialUid,
    materialUidList = materialUidList
)

internal fun Array<out QuestionDto>.toDataModel(): Array<out QuestionEntity> =
    map(QuestionDto::toDataModel).toTypedArray()

