/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.domain.repository

import dev.sertan.android.ram.appmemory.domain.model.MatchingQuestionDto

internal interface MatchingQuestionRepository {

    suspend fun getQuestions(update: Boolean = false): Result<List<MatchingQuestionDto>>

    suspend fun saveMatchingQuestionToLocal(question: MatchingQuestionDto): Boolean

    suspend fun refreshQuestions(): Boolean
}
