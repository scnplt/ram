/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.ui.posedetection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.pose.Pose
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.appmovements.domain.GetMotionsUseCase
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val CHECK_PER_DETECTION = 8

@HiltViewModel
internal class PoseDetectionViewModel @Inject constructor(
    getMotionsUseCase: GetMotionsUseCase,
    private val textToSpeechUseCase: TextToSpeechUseCase
) : ViewModel() {

    var isValidationActive = true
    private var trueDetectionCount = 0
    private val motionIndex = MutableStateFlow(0)

    private val motions = getMotionsUseCase()

    val uiState: StateFlow<PoseDetectionUiState> = flow {
        motionIndex.collect { index ->
            val state = PoseDetectionUiState.getState(index, motions)
            textToSpeechUseCase.speak(state.motion?.descriptionResId)
            emit(state)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = PoseDetectionUiState.initialState()
    )

    fun checkMotion(pose: Pose, callback: suspend (isCorrect: Boolean) -> Unit) {
        if (!isValidationActive) return
        val result = uiState.value.motion?.check(pose)
        if (result == true) trueDetectionCount++ else trueDetectionCount = 0
        viewModelScope.launch { callback(trueDetectionCount >= CHECK_PER_DETECTION) }
    }

    fun resetCount() {
        trueDetectionCount = 0
    }

    fun goToNextMotion() {
        motionIndex.update { it.inc() }
        resetCount()
        isValidationActive = true
    }

    fun stopSpeech(): Unit = textToSpeechUseCase.stopSpeech()
}
