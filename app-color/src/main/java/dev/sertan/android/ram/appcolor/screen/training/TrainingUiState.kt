/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.training

import dev.sertan.android.ram.coreui.model.Material

internal data class TrainingUiState(
    val material: Material? = null,
    val isBackButtonVisible: Boolean = false,
    val isForwardButtonVisible: Boolean = false,
    val isFinishButtonVisible: Boolean = false
)
