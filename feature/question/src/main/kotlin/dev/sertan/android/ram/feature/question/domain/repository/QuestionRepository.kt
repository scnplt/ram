/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.question.domain.repository

import dev.sertan.android.ram.feature.question.domain.model.QuestionDto

interface QuestionRepository {

    suspend fun getQuestions(update: Boolean = false): Result<List<QuestionDto>>

    suspend fun saveQuestionToLocal(question: QuestionDto): Boolean

    suspend fun refreshQuestions(): Boolean
}
