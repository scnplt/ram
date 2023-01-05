/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.domain.repository

import dev.sertan.android.ram.appnumber.domain.model.SectionDto

internal interface SectionRepository {

    suspend fun getSections(update: Boolean = false): Result<List<SectionDto>>

    suspend fun saveSectionToLocal(section: SectionDto): Boolean

    suspend fun refreshSections(): Boolean
}
