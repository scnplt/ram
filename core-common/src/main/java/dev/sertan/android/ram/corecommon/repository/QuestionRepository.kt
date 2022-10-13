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

    suspend fun getAllQuestions(): Result<List<QuestionDto>>

    suspend fun saveQuestion(vararg questionDtoArray: QuestionDto): Result<Unit>

    suspend fun deleteQuestion(vararg questionDtoArray: QuestionDto): Result<Unit>

    suspend fun updateQuestion(questionDto: QuestionDto): Result<Unit>
}
