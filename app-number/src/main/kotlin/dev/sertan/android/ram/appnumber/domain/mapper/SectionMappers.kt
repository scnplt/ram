/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.domain.mapper

import dev.sertan.android.ram.appnumber.domain.model.SectionDto
import dev.sertan.android.ram.appnumber.ui.model.Section

internal fun SectionDto.toUIModel(): Section = Section(
    uid = uid,
    minNumber = minNumber,
    maxNumber = maxNumber,
    step = step
)
