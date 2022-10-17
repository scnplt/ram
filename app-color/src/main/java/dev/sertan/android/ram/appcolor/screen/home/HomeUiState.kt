/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.home

import dev.sertan.android.ram.coreui.util.OneTimeState

internal data class HomeUiState(
    val isVolumeActive: Boolean = true,
    val errorMessage: OneTimeState<String> = OneTimeState()
)
