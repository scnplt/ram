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
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.domain.usecase.GetUserConfigsUseCase
import dev.sertan.android.ram.domain.usecase.UpdateUserConfigsUseCase
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getUserConfigsUseCase: GetUserConfigsUseCase,
    private val updateUserConfigsUseCase: UpdateUserConfigsUseCase
) : ViewModel() {

    private var voiceSupportJob: Job? = null

    private val _isVoiceSupportActive = MutableStateFlow<Boolean?>(null)
    val isVoiceSupportState = _isVoiceSupportActive.asStateFlow()

    fun changeVoiceSupportState(): Unit {
        voiceSupportJob?.cancel()
        voiceSupportJob = viewModelScope.launch {

        }
    }
}
