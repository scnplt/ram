/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class HomeViewModel @Inject constructor() : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState get() = _homeUiState.asStateFlow()

    fun changeVolumeState() {
        _homeUiState.update { it.copy(isVolumeActive = !it.isVolumeActive) }
    }
}
