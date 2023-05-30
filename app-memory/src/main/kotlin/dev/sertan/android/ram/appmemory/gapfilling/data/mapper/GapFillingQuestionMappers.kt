/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.gapfilling.data.mapper

import dev.sertan.android.ram.appmemory.gapfilling.data.datasource.local.GapFillingQuestionEntity
import dev.sertan.android.ram.appmemory.gapfilling.data.datasource.remote.NetworkGapFillingQuestion
import dev.sertan.android.ram.appmemory.gapfilling.domain.model.GapFillingQuestionDto

internal fun GapFillingQuestionEntity.toDto(): GapFillingQuestionDto = GapFillingQuestionDto(
    uid = uid,
    content = content,
    materialUidList = materialUidList,
    correctMaterialUid = correctMaterialUid,
    contentMaterialUid = contentMaterialUid
)

internal fun GapFillingQuestionDto.toEntity(): GapFillingQuestionEntity = GapFillingQuestionEntity(
    uid = uid,
    content = content,
    correctMaterialUid = correctMaterialUid,
    materialUidList = materialUidList,
    contentMaterialUid = contentMaterialUid
)

internal fun NetworkGapFillingQuestion.toEntity(): GapFillingQuestionEntity =
    GapFillingQuestionEntity(
        uid = uid!!,
        content = content!!,
        correctMaterialUid = correctAnswerUid!!,
        materialUidList = materialUidList!!,
        contentMaterialUid = contentMaterialUid!!
    )
