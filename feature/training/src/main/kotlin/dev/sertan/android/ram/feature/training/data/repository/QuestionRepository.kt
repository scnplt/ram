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
import dev.sertan.android.ram.core.data.FirestoreDataReceiver
import dev.sertan.android.ram.feature.training.data.datasource.local.QuestionDao
import dev.sertan.android.ram.feature.training.data.datasource.local.QuestionEntity
import dev.sertan.android.ram.feature.training.data.datasource.remote.NetworkQuestion
import dev.sertan.android.ram.feature.training.data.mapper.toDto
import dev.sertan.android.ram.feature.training.data.mapper.toEntity
import dev.sertan.android.ram.feature.training.domain.model.QuestionDto
import javax.inject.Inject

internal class QuestionRepository @Inject constructor(
    private val dataReceiver: FirestoreDataReceiver<NetworkQuestion>,
    private val dao: QuestionDao,
    private val logger: RamLogger
) {

    suspend fun getQuestions(update: Boolean = false): Result<List<QuestionDto>> =
        tryGetWithResult(logger) {
            if (update) updateQuestionsFromRemote()
            dao.getAll().map(QuestionEntity::toDto)
        }

    private suspend fun updateQuestionsFromRemote() {
        val remoteData = dataReceiver.getAll()
        dao.deleteAll()
        remoteData.tryMapNotNull(logger = logger, transform = NetworkQuestion::toEntity)
            .forEach { dao.insert(questionEntity = it) }
    }
}
