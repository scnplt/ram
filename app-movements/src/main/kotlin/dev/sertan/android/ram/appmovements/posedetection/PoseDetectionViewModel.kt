/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.posedetection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.pose.Pose
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val RESET_PER_DETECTION = 8

@HiltViewModel
internal class PoseDetectionViewModel @Inject constructor(
    private val textToSpeechUseCase: TextToSpeechUseCase
) : ViewModel() {

    private var count = 0

    private val motions = listOf(
        RaiseRightHandMotion(),
        RaiseLeftHandMotion()
    )

    private val motionIndex = MutableStateFlow(0)

    private val motionFlow: StateFlow<Motion?> = flow {
        motionIndex.collect { index ->
            val motion = motions.getOrNull(index)
            emit(motion)
            textToSpeechUseCase.speak(motion?.descriptionResId)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = null
    )

    fun checkMotion(pose: Pose, callback: suspend (Boolean) -> Unit) {
        val result = motionFlow.value?.check(pose)
        if (result == true) count++ else count = 0
        viewModelScope.launch { callback(count >= RESET_PER_DETECTION) }
    }

    fun resetCount() {
        count = 0
    }

    fun goToNextMotion() {
        motionIndex.update { it.inc() }
        resetCount()
    }
}
