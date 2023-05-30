/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.gapfilling.data.repository

import dev.sertan.android.ram.appmemory.gapfilling.data.datasource.local.GapFillingQuestionDao
import dev.sertan.android.ram.appmemory.gapfilling.data.datasource.local.GapFillingQuestionEntity
import dev.sertan.android.ram.appmemory.gapfilling.data.datasource.remote.FirestoreGapFillingQuestionService
import dev.sertan.android.ram.appmemory.gapfilling.data.datasource.remote.NetworkGapFillingQuestion
import dev.sertan.android.ram.appmemory.gapfilling.data.mapper.toDto
import dev.sertan.android.ram.appmemory.gapfilling.data.mapper.toEntity
import dev.sertan.android.ram.appmemory.gapfilling.domain.model.GapFillingQuestionDto
import dev.sertan.android.ram.appmemory.gapfilling.domain.repository.GapFillingQuestionRepository
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapNotNull
import dev.sertan.android.ram.core.common.tryWithLogger
import javax.inject.Inject

internal class DefaultGapFillingQuestionRepository @Inject constructor(
    private val gapFillingQuestionDao: GapFillingQuestionDao,
    private val matchingQuestionService: FirestoreGapFillingQuestionService,
    private val logger: RamLogger
) : GapFillingQuestionRepository {

    override suspend fun getQuestions(update: Boolean): Result<List<GapFillingQuestionDto>> =
        tryGetWithResult(logger) {
            if (update) updateQuestionsFromRemote()
            gapFillingQuestionDao.getAll().map(GapFillingQuestionEntity::toDto)
        }

    override suspend fun saveMatchingQuestionToLocal(question: GapFillingQuestionDto): Boolean =
        tryWithLogger(logger) {
            gapFillingQuestionDao.insert(gapFillingQuestionEntity = question.toEntity())
        }

    override suspend fun refreshQuestions(): Boolean =
        tryWithLogger(logger) { updateQuestionsFromRemote() }

    private suspend fun updateQuestionsFromRemote() {
        val remoteData = matchingQuestionService.getQuestions()
        gapFillingQuestionDao.deleteAll()
        remoteData.tryMapNotNull(logger = logger, transform = NetworkGapFillingQuestion::toEntity)
            .forEach { gapFillingQuestionDao.insert(gapFillingQuestionEntity = it) }
    }
}
