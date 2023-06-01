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
import dev.sertan.android.ram.appmemory.data.datasource.remote.NetworkMatchingQuestion
import dev.sertan.android.ram.appmemory.data.mapper.toDto
import dev.sertan.android.ram.appmemory.data.mapper.toEntity
import dev.sertan.android.ram.appmemory.domain.model.MatchingQuestionDto
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapNotNull
import dev.sertan.android.ram.core.common.tryWithLogger
import dev.sertan.android.ram.core.data.FirestoreDataReceiver
import javax.inject.Inject

internal class MatchingQuestionRepository @Inject constructor(
    private val dao: MatchingQuestionDao,
    private val service: FirestoreDataReceiver<NetworkMatchingQuestion>,
    private val logger: RamLogger
) {

    suspend fun getQuestions(update: Boolean = false): Result<List<MatchingQuestionDto>> =
        tryGetWithResult(logger) {
            if (update) updateQuestionsFromRemote()
            dao.getAll().map(MatchingQuestionEntity::toDto)
        }

    suspend fun saveMatchingQuestionToLocal(question: MatchingQuestionDto): Boolean =
        tryWithLogger(logger) { dao.insert(matchingQuestionEntity = question.toEntity()) }

    suspend fun refreshQuestions(): Boolean =
        tryWithLogger(logger) { updateQuestionsFromRemote() }

    private suspend fun updateQuestionsFromRemote() {
        val remoteData = service.getAll()
        dao.deleteAll()
        remoteData.tryMapNotNull(logger = logger, transform = NetworkMatchingQuestion::toEntity)
            .forEach { dao.insert(matchingQuestionEntity = it) }
    }
}
