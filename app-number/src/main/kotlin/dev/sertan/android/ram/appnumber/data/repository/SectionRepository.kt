/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.data.repository

import dev.sertan.android.ram.appnumber.data.datasource.local.SectionDao
import dev.sertan.android.ram.appnumber.data.datasource.local.SectionEntity
import dev.sertan.android.ram.appnumber.data.datasource.remote.NetworkSection
import dev.sertan.android.ram.appnumber.data.mapper.toDto
import dev.sertan.android.ram.appnumber.data.mapper.toEntity
import dev.sertan.android.ram.appnumber.domain.model.SectionDto
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapNotNull
import dev.sertan.android.ram.core.data.FirestoreDataReceiver
import javax.inject.Inject

internal class SectionRepository @Inject constructor(
    private val dataReceiver: FirestoreDataReceiver<NetworkSection>,
    private val dao: SectionDao,
    private val logger: RamLogger
) {

    suspend fun getSections(update: Boolean = false): Result<List<SectionDto>> =
        tryGetWithResult(logger) {
            if (update) updateSectionsFromRemote()
            dao.getAll().map(SectionEntity::toDto)
        }

    private suspend fun updateSectionsFromRemote() {
        val remoteData = dataReceiver.getAll()
        dao.deleteAll()
        remoteData.tryMapNotNull(logger = logger, transform = NetworkSection::toEntity)
            .forEach { dao.insert(sectionEntity = it) }
    }
}
