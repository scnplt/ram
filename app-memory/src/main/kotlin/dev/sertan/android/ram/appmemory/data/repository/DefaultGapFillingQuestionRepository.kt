/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.data.repository

import dev.sertan.android.ram.appmemory.data.datasource.local.dao.GapFillingQuestionDao
import dev.sertan.android.ram.appmemory.data.datasource.local.model.GapFillingQuestionEntity
import dev.sertan.android.ram.appmemory.data.datasource.remote.model.NetworkGapFillingQuestion
import dev.sertan.android.ram.appmemory.data.datasource.remote.service.FirestoreGapFillingQuestionService
import dev.sertan.android.ram.appmemory.data.mapper.toDto
import dev.sertan.android.ram.appmemory.data.mapper.toEntity
import dev.sertan.android.ram.appmemory.domain.model.GapFillingQuestionDto
import dev.sertan.android.ram.appmemory.domain.repository.GapFillingQuestionRepository
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapNotNull
import dev.sertan.android.ram.core.common.tryWithLogger
import javax.inject.Inject

internal class DefaultGapFillingQuestionRepository @Inject constructor(
    private val dao: GapFillingQuestionDao,
    private val service: FirestoreGapFillingQuestionService,
    private val logger: RamLogger
) : GapFillingQuestionRepository {

    override suspend fun getQuestions(update: Boolean): Result<List<GapFillingQuestionDto>> =
        tryGetWithResult(logger) {
            if (update) updateQuestionsFromRemote()
            dao.getAll().map(GapFillingQuestionEntity::toDto)
        }

    override suspend fun saveQuestionToLocal(question: GapFillingQuestionDto): Boolean =
        tryWithLogger(logger) { dao.insert(gapFillingQuestionEntity = question.toEntity()) }

    override suspend fun refreshQuestions(): Boolean =
        tryWithLogger(logger) { updateQuestionsFromRemote() }

    private suspend fun updateQuestionsFromRemote() {
        val remoteData = service.getQuestions()
        dao.deleteAll()
        remoteData.tryMapNotNull(logger = logger, transform = NetworkGapFillingQuestion::toEntity)
            .forEach { dao.insert(gapFillingQuestionEntity = it) }
    }
}
