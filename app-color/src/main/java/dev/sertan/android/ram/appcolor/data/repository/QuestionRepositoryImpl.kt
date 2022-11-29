/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.data.repository

import dev.sertan.android.ram.appcolor.data.database.dao.QuestionDao
import dev.sertan.android.ram.appcolor.data.mapper.toDataModel
import dev.sertan.android.ram.appcolor.data.mapper.toDomainModel
import dev.sertan.android.ram.appcolor.data.service.QuestionService
import dev.sertan.android.ram.appcolor.domain.model.QuestionDto
import dev.sertan.android.ram.appcolor.domain.repository.QuestionRepository
import dev.sertan.android.ram.core.util.tryGetResultWithLog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class QuestionRepositoryImpl @Inject constructor(
    private val questionDao: QuestionDao,
    private val questionService: QuestionService
) : QuestionRepository {

    override suspend fun getQuestionsFromLocal(): Result<List<QuestionDto>> =
        tryGetResultWithLog { questionDao.getAll().toDomainModel() }

    override suspend fun getQuestionsFromRemote(): Result<List<QuestionDto>> =
        tryGetResultWithLog { questionService.getAllQuestions().mapNotNull { it.toDomainModel() } }

    override suspend fun saveQuestionToLocal(vararg questionArray: QuestionDto): Result<Unit> =
        tryGetResultWithLog { questionDao.insert(questionArray = questionArray.toDataModel()) }

    override suspend fun deleteQuestionFromLocal(vararg questionArray: QuestionDto): Result<Unit> =
        tryGetResultWithLog { questionDao.delete(questionArray = questionArray.toDataModel()) }

    override suspend fun updateLocalQuestion(questionDto: QuestionDto): Result<Unit> =
        tryGetResultWithLog { questionDao.update(questionEntity = questionDto.toDataModel()) }
}
