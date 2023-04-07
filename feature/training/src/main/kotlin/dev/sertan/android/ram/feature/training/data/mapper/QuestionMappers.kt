/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training.data.mapper

import dev.sertan.android.ram.feature.training.data.datasource.local.model.QuestionEntity
import dev.sertan.android.ram.feature.training.data.datasource.remote.model.NetworkQuestion
import dev.sertan.android.ram.feature.training.domain.model.QuestionDto

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
