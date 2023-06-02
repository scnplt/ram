/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.model

import dev.sertan.android.ram.feature.material.ui.Material

internal data class MatchingQuestion(
    val uid: String,
    val content: String,
    val materials: List<Material>
)
