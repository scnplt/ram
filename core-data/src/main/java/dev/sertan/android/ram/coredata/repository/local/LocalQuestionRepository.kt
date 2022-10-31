/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.repository.local

import dev.sertan.android.ram.corecommon.model.QuestionDto
import dev.sertan.android.ram.corecommon.repository.QuestionRepository
import dev.sertan.android.ram.coredata.database.dao.QuestionDao
import dev.sertan.android.ram.coredata.model.mapper.toDataModel
import dev.sertan.android.ram.coredata.model.mapper.toDomainModel

internal class LocalQuestionRepository(
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
