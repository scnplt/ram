/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.ui.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import dev.sertan.android.ram.feature.material.domain.usecase.GetMaterialsUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class TrainingViewModel @Inject constructor(
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val textToSpeechUseCase: TextToSpeechUseCase
) : ViewModel() {

    private val materialIndex = MutableStateFlow(0)

    val uiState: StateFlow<TrainingUiState> = flow {
        val materials = getMaterialsUseCase()
        materialIndex.collect { index ->
            textToSpeechUseCase.checkStateAndSpeak(materials.getOrNull(index)?.description)
            emit(TrainingUiState.getState(materials, index))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TrainingUiState.initialState()
    )

    fun goToNextMaterial(): Unit = materialIndex.update { it.inc() }

    fun goToPreviousMaterial(): Unit = materialIndex.update { it.dec() }

    fun speakCurrentMaterialDescription(): Unit =
        textToSpeechUseCase.speak(uiState.value.material?.description)

    fun stopSpeech(): Unit = textToSpeechUseCase.stopSpeech()
}
