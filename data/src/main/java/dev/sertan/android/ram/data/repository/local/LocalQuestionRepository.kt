/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.repository.local

import dev.sertan.android.ram.data.database.dao.QuestionDao
import dev.sertan.android.ram.data.model.mapper.toDataModel
import dev.sertan.android.ram.data.model.mapper.toDomainModel
import dev.sertan.android.ram.coredomain.di.LocalDataSource
import dev.sertan.android.ram.coredomain.model.QuestionDto
import dev.sertan.android.ram.coredomain.repository.QuestionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@LocalDataSource
internal class LocalQuestionRepository @Inject constructor(
    private val questionDao: QuestionDao
) : QuestionRepository {

    override suspend fun getAllQuestions(): Result<List<QuestionDto>> = runCatching {
        questionDao.getAll().toDomainModel()
    }

    override suspend fun saveQuestion(vararg questionDtoArray: QuestionDto): Result<Unit> {
        return runCatching {
            questionDao.insert(questionEntityArray = questionDtoArray.toDataModel())
        }
    }

    override suspend fun deleteQuestion(vararg questionDtoArray: QuestionDto): Result<Unit> {
        return runCatching {
            questionDao.delete(questionEntityArray = questionDtoArray.toDataModel())
        }
    }

    override suspend fun updateQuestion(questionDto: QuestionDto): Result<Unit> = runCatching {
        questionDao.update(questionEntity = questionDto.toDataModel())
    }
}
