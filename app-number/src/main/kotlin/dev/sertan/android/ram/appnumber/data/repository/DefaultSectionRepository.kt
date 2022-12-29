/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.data.repository

import dev.sertan.android.ram.appnumber.data.database.dao.SectionDao
import dev.sertan.android.ram.appnumber.data.database.model.SectionEntity
import dev.sertan.android.ram.appnumber.data.mapper.toDto
import dev.sertan.android.ram.appnumber.data.mapper.toEntity
import dev.sertan.android.ram.appnumber.data.service.SectionService
import dev.sertan.android.ram.appnumber.data.service.model.NetworkSection
import dev.sertan.android.ram.appnumber.domain.model.SectionDto
import dev.sertan.android.ram.appnumber.domain.repository.SectionRepository
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapNotNull
import dev.sertan.android.ram.core.common.tryWithLogger
import javax.inject.Inject

internal class DefaultSectionRepository @Inject constructor(
    private val sectionDao: SectionDao,
    private val sectionService: SectionService,
    private val logger: RamLogger
) : SectionRepository {

    override suspend fun getSections(update: Boolean): Result<List<SectionDto>> =
        tryGetWithResult(logger) {
            if (update) updateSectionsFromRemote()
            sectionDao.getAll().map(SectionEntity::toDto)
        }

    override suspend fun saveSectionToLocal(section: SectionDto): Boolean =
        tryWithLogger(logger) { sectionDao.insert(sectionEntity = section.toEntity()) }

    override suspend fun refreshSections(): Boolean =
        tryWithLogger(logger) { updateSectionsFromRemote() }

    private suspend fun updateSectionsFromRemote() {
        val remoteData = sectionService.getSections()
        sectionDao.deleteAll()
        remoteData.tryMapNotNull(logger = logger, transform = NetworkSection::toEntity)
            .forEach { sectionDao.insert(sectionEntity = it) }
    }
}
