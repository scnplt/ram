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
import dev.sertan.android.ram.coreui.model.UserConfigs
import dev.sertan.android.ram.coreui.util.OneTimeState
import dev.sertan.android.ram.domain.usecase.GetUserConfigsUseCase
import dev.sertan.android.ram.domain.usecase.UpdateUserSettingsUseCase
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    getUserConfigsUseCase: GetUserConfigsUseCase,
    private val updateUserSettingsUseCase: UpdateUserSettingsUseCase
) : ViewModel() {

    private var voiceSupportJob: Job? = null

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState

    val isTrainingCompletedBefore: Boolean
        get() = uiState.value.isTrainingCompletedBefore

    init {
        viewModelScope.launch {
            getUserConfigsUseCase().collect { result ->
                result.onSuccess { configs ->
                    _uiState.update {
                        it.copy(
                            isVolumeActive = configs.isVoiceSupportActive,
                            isTrainingCompletedBefore = configs.trainingCompletedBefore
                        )
                    }
                }.onFailure { exception ->
                    // TODO add the exception to the log
                    _uiState.update {
                        it.copy(message = OneTimeState(exception.localizedMessage))
                    }
                }
            }
        }
    }

    fun changeVoiceSupportState() {
        voiceSupportJob?.cancel()
        voiceSupportJob = viewModelScope.launch {
            uiState.value.toUserConfigs().let {
                updateUserSettingsUseCase(
                    userConfigs = it.copy(isVoiceSupportActive = !it.isVoiceSupportActive)
                )
            }
        }
    }

    private fun HomeUiState.toUserConfigs(): UserConfigs {
        return UserConfigs(
            isVoiceSupportActive = isVolumeActive,
            trainingCompletedBefore = isTrainingCompletedBefore
        )
    }
}
