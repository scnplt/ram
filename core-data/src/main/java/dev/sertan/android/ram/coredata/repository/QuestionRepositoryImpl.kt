/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.repository

import dev.sertan.android.ram.corecommon.model.QuestionDto
import dev.sertan.android.ram.corecommon.repository.QuestionRepository
import dev.sertan.android.ram.corecommon.util.tryGetResultWithLog
import dev.sertan.android.ram.coredata.database.dao.QuestionDao
import dev.sertan.android.ram.coredata.mapper.toDataModel
import dev.sertan.android.ram.coredata.mapper.toDomainModel
import dev.sertan.android.ram.coredata.service.QuestionService
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
