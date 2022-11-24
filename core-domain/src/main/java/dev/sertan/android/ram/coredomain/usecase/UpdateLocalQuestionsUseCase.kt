/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredomain.usecase

import dev.sertan.android.ram.corecommon.repository.QuestionRepository
import javax.inject.Inject

class UpdateLocalQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {

    suspend operator fun invoke(): Boolean = with(questionRepository) {
        getQuestionsFromRemote().getOrNull()
            ?.let { saveQuestionToLocal(questionArray = it.toTypedArray()).isSuccess } ?: false
    }
}
