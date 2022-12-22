/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.domain.mapper

import dev.sertan.android.ram.appselection.domain.model.MaterialDto
import dev.sertan.android.ram.appselection.ui.model.Material

internal fun MaterialDto.toUIModel(): Material = Material(
    uid = uid,
    description = description.replaceFirstChar { it.uppercase() },
    mediaUrl = mediaUrl,
    attribution = attribution
)
