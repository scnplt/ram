/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.corecommon.repository

import dev.sertan.android.ram.corecommon.model.QuestionDto

interface QuestionRepository {

    suspend fun getQuestionsFromLocal(): Result<List<QuestionDto>>

    suspend fun getQuestionsFromRemote(): Result<List<QuestionDto>>

    suspend fun saveQuestionToLocal(vararg questionArray: QuestionDto): Result<Unit>

    suspend fun deleteQuestionFromLocal(vararg questionArray: QuestionDto): Result<Unit>

    suspend fun updateLocalQuestion(questionDto: QuestionDto): Result<Unit>
}
