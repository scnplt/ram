/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.data.mapper

import dev.sertan.android.ram.appnumber.data.database.model.QuestionEntity
import dev.sertan.android.ram.appnumber.data.service.model.NetworkQuestion
import dev.sertan.android.ram.appnumber.domain.model.QuestionDto

internal fun QuestionEntity.toDto(shuffle: Boolean = true): QuestionDto = QuestionDto(
    uid = uid,
    content = content,
    materialUidList = if (shuffle) materialUidList.shuffled() else materialUidList,
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
