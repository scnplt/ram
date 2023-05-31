/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.data.repository

import dev.sertan.android.ram.appmemory.data.datasource.local.dao.MatchingQuestionDao
import dev.sertan.android.ram.appmemory.data.datasource.local.model.MatchingQuestionEntity
import dev.sertan.android.ram.appmemory.data.datasource.remote.model.NetworkMatchingQuestion
import dev.sertan.android.ram.appmemory.data.datasource.remote.service.FirestoreMatchingQuestionService
import dev.sertan.android.ram.appmemory.data.mapper.toDto
import dev.sertan.android.ram.appmemory.data.mapper.toEntity
import dev.sertan.android.ram.appmemory.domain.model.MatchingQuestionDto
import dev.sertan.android.ram.appmemory.domain.repository.MatchingQuestionRepository
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapNotNull
import dev.sertan.android.ram.core.common.tryWithLogger
import javax.inject.Inject

internal class DefaultMatchingQuestionRepository @Inject constructor(
    private val dao: MatchingQuestionDao,
    private val service: FirestoreMatchingQuestionService,
    private val logger: RamLogger
) : MatchingQuestionRepository {

    override suspend fun getQuestions(update: Boolean): Result<List<MatchingQuestionDto>> =
        tryGetWithResult(logger) {
            if (update) updateQuestionsFromRemote()
            dao.getAll().map(MatchingQuestionEntity::toDto)
        }

    override suspend fun saveMatchingQuestionToLocal(question: MatchingQuestionDto): Boolean =
        tryWithLogger(logger) { dao.insert(matchingQuestionEntity = question.toEntity()) }

    override suspend fun refreshQuestions(): Boolean =
        tryWithLogger(logger) { updateQuestionsFromRemote() }

    private suspend fun updateQuestionsFromRemote() {
        val remoteData = service.getQuestions()
        dao.deleteAll()
        remoteData.tryMapNotNull(logger = logger, transform = NetworkMatchingQuestion::toEntity)
            .forEach { dao.insert(matchingQuestionEntity = it) }
    }
}
