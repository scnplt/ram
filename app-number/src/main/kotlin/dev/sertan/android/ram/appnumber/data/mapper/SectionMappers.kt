/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.data.mapper

import dev.sertan.android.ram.appnumber.data.datasource.local.SectionEntity
import dev.sertan.android.ram.appnumber.data.datasource.remote.NetworkSection
import dev.sertan.android.ram.appnumber.domain.model.SectionDto

internal fun SectionEntity.toDto(): SectionDto = SectionDto(
    uid = uid,
    minNumber = minNumber,
    maxNumber = maxNumber,
    step = step
)

internal fun SectionDto.toEntity(): SectionEntity = SectionEntity(
    uid = uid,
    minNumber = minNumber,
    maxNumber = maxNumber,
    step = step
)

internal fun NetworkSection.toEntity(): SectionEntity = SectionEntity(
    uid = uid!!,
    minNumber = minNumber!!,
    maxNumber = maxNumber!!,
    step = step!!
)
