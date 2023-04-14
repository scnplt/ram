/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training.data.repository

import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapNotNull
import dev.sertan.android.ram.core.common.tryWithLogger
import dev.sertan.android.ram.feature.training.data.datasource.local.QuestionDao
import dev.sertan.android.ram.feature.training.data.datasource.local.QuestionEntity
import dev.sertan.android.ram.feature.training.data.datasource.remote.FirestoreQuestionService
import dev.sertan.android.ram.feature.training.data.datasource.remote.NetworkQuestion
import dev.sertan.android.ram.feature.training.data.mapper.toDto
import dev.sertan.android.ram.feature.training.data.mapper.toEntity
import dev.sertan.android.ram.feature.training.domain.model.QuestionDto
import dev.sertan.android.ram.feature.training.domain.repository.QuestionRepository
import javax.inject.Inject

internal class DefaultQuestionRepository @Inject constructor(
    private val questionDao: QuestionDao,
    private val questionService: FirestoreQuestionService,
    private val logger: RamLogger
) : QuestionRepository {

    override suspend fun getQuestions(update: Boolean): Result<List<QuestionDto>> =
        tryGetWithResult(logger) {
            if (update) updateQuestionsFromRemote()
            questionDao.getAll().map(QuestionEntity::toDto)
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
