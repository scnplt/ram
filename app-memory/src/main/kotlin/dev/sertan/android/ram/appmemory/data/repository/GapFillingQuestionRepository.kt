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
import dev.sertan.android.ram.appmemory.data.datasource.remote.NetworkGapFillingQuestion
import dev.sertan.android.ram.appmemory.data.mapper.toDto
import dev.sertan.android.ram.appmemory.data.mapper.toEntity
import dev.sertan.android.ram.appmemory.domain.model.GapFillingQuestionDto
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapNotNull
import dev.sertan.android.ram.core.common.tryWithLogger
import dev.sertan.android.ram.core.data.FirestoreDataReceiver
import javax.inject.Inject

internal class GapFillingQuestionRepository @Inject constructor(
    private val dao: GapFillingQuestionDao,
    private val service: FirestoreDataReceiver<NetworkGapFillingQuestion>,
    private val logger: RamLogger
) {

    suspend fun getQuestions(update: Boolean = false): Result<List<GapFillingQuestionDto>> =
        tryGetWithResult(logger) {
            if (update) updateQuestionsFromRemote()
            dao.getAll().map(GapFillingQuestionEntity::toDto)
        }

    suspend fun saveQuestionToLocal(question: GapFillingQuestionDto): Boolean =
        tryWithLogger(logger) { dao.insert(gapFillingQuestionEntity = question.toEntity()) }

    suspend fun refreshQuestions(): Boolean =
        tryWithLogger(logger) { updateQuestionsFromRemote() }

    private suspend fun updateQuestionsFromRemote() {
        val remoteData = service.getAll()
        dao.deleteAll()
        remoteData.tryMapNotNull(logger = logger, transform = NetworkGapFillingQuestion::toEntity)
            .forEach { dao.insert(gapFillingQuestionEntity = it) }
    }
}
