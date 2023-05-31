/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.data.mapper

import dev.sertan.android.ram.appmemory.data.datasource.local.model.MatchingQuestionEntity
import dev.sertan.android.ram.appmemory.data.datasource.remote.model.NetworkMatchingQuestion
import dev.sertan.android.ram.appmemory.domain.model.MatchingQuestionDto

internal fun MatchingQuestionEntity.toDto(): MatchingQuestionDto = MatchingQuestionDto(
    uid = uid,
    content = content,
    materialUidList = materialUidList,
    correctAnswerUid = correctAnswerUid
)

internal fun MatchingQuestionDto.toEntity(): MatchingQuestionEntity = MatchingQuestionEntity(
    uid = uid,
    content = content,
    materialUidList = materialUidList,
    correctAnswerUid = correctAnswerUid
)

internal fun NetworkMatchingQuestion.toEntity(): MatchingQuestionEntity = MatchingQuestionEntity(
    uid = uid!!,
    content = content!!,
    materialUidList = materialUidList!!,
    correctAnswerUid = correctAnswerUid!!
)
