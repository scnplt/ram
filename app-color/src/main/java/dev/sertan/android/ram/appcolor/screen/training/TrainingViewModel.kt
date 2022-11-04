/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.training

import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.coredomain.usecase.GetMaterialsUseCase
import dev.sertan.android.ram.coredomain.usecase.VoiceSupportUseCase
import dev.sertan.android.ram.coreui.util.extension.speak
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class TrainingViewModel @Inject constructor(
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val voiceSupportUseCase: VoiceSupportUseCase,
    private val textToSpeech: TextToSpeech
) : ViewModel() {

    private val materialIndex = MutableStateFlow(0)

    val uiState: StateFlow<TrainingUiState> = flow {
        val materials = getMaterialsUseCase()
        materialIndex.collect { emit(TrainingUiState.getState(materials = materials, index = it)) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TrainingUiState.initialState()
    )

    init {
        viewModelScope.launch {
            if (!voiceSupportUseCase.getVoiceSupportState()) return@launch
            uiState.collect { speakCurrentMaterialDescription() }
        }
    }

    fun goToNextMaterial(): Unit = materialIndex.update { it.inc() }

    fun goToPreviousMaterial(): Unit = materialIndex.update { it.dec() }

    fun speakCurrentMaterialDescription() = textToSpeech.speak(uiState.value.material?.description)

    fun stopSpeech() {
        textToSpeech.stop()
    }
}
