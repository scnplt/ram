/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.feature.question.data.repository

import dev.sertan.android.feature.question.data.database.dao.QuestionDao
import dev.sertan.android.feature.question.data.database.model.QuestionEntity
import dev.sertan.android.feature.question.data.mapper.toDto
import dev.sertan.android.feature.question.data.mapper.toEntity
import dev.sertan.android.feature.question.data.service.QuestionService
import dev.sertan.android.feature.question.data.service.model.NetworkQuestion
import dev.sertan.android.feature.question.domain.model.QuestionDto
import dev.sertan.android.feature.question.domain.repository.QuestionRepository
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapNotNull
import dev.sertan.android.ram.core.common.tryWithLogger
import javax.inject.Inject

internal class DefaultQuestionRepository @Inject constructor(
    private val questionDao: QuestionDao,
    private val questionService: QuestionService,
    private val logger: RamLogger
) : QuestionRepository {

    override suspend fun getQuestions(update: Boolean): Result<List<QuestionDto>> =
        tryGetWithResult(logger) {
            if (update) updateQuestionsFromRemote()
            questionDao.getAll().map(QuestionEntity::toDto).shuffled()
        }

    override suspend fun saveQuestionToLocal(question: QuestionDto): Boolean =
        tryWithLogger(logger) { questionDao.insert(questionEntity = question.toEntity()) }

    override suspend fun refreshQuestions(): Boolean =
        tryWithLogger(logger) { updateQuestionsFromRemote() }

    private suspend fun updateQuestionsFromRemote() {
        val remoteData = questionService.getQuestions()
        questionDao.deleteAll()
        remoteData.tryMapNotNull(logger = logger, transform = NetworkQuestion::toEntity)
            .forEach { questionDao.insert(questionEntity = it) }
    }
}