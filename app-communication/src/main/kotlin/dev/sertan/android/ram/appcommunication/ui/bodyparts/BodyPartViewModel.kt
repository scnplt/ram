/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.bodyparts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.appcommunication.R
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class BodyPartViewModel @Inject constructor(
    private val textToSpeechUseCase: TextToSpeechUseCase
) : ViewModel() {

    var listener: AnswerListener? = null

    private val partIndex = MutableStateFlow(0)

    private var isFacePart = false

    @Suppress("MagicNumber")
    private val fullBodyPartList by lazy {
        listOf(
            BodyPart(R.string.show_head, 0.4785f, 0.044f, 0.7689f, 0.1385f),
            BodyPart(R.string.show_left_hand, 0.255f, 0.44f, 0.54f, 0.5f),
            BodyPart(R.string.show_right_hand, 0.82f, 0.4f, 0.975f, 0.47f),
            BodyPart(R.string.show_left_feet, 0.3f, 0.93f, 0.53f, 1f),
            BodyPart(R.string.show_right_feet, 0.62f, 0.92f, 0.955f, 0.98f)
        )
    }

    @Suppress("MagicNumber")
    private val facePartList by lazy {
        listOf(
            BodyPart(R.string.show_left_eye, 0.485f, 0.442f, 0.593f, 0.47f),
            BodyPart(R.string.show_right_eye, 0.673f, 0.45f, 0.761f, 0.48f),
            BodyPart(R.string.show_mouth, 0.544f, 0.564f, 0.71f, 0.62f),
            BodyPart(R.string.show_ears, 0.3f, 0.45f, 0.4f, 0.564f),
            BodyPart(R.string.show_nose, 0.593f, 0.442f, 0.673f, 0.55f)
        )
    }

    private val parts = mutableListOf(elements = fullBodyPartList.toTypedArray())

    val uiState: StateFlow<BodyUiState> = flow {
        partIndex.collect { index ->
            val part = parts.getOrNull(index)
            textToSpeechUseCase.checkStateAndSpeak(part?.contentResId)
            val state = BodyUiState(
                bodyImageResId = if (isFacePart) R.drawable.head else R.drawable.full_body,
                part = part,
                isForwardButtonInvisible = index >= fullBodyPartList.lastIndex,
                isFinishButtonVisible = index >= fullBodyPartList.lastIndex
            )
            emit(state)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = BodyUiState.initialState()
    )

    fun goToNextQuestion(): Unit = partIndex.update { it.inc() }

    fun checkTouchPoint(xRatio: Float, yRatio: Float) {
        uiState.value.part?.let { part ->
            val isCorrect = xRatio in part.left..part.right && yRatio in part.top..part.bottom
            listener?.onCheckedAnswer(isCorrect)
        }
    }

    fun goToNextPartOrElse(block: () -> Unit) {
        if (isFacePart) return block()
        parts.clear()
        parts.addAll(facePartList)
        isFacePart = true
        partIndex.update { 0 }
    }

    fun stopSpeech(): Unit = textToSpeechUseCase.stopSpeech()

    fun interface AnswerListener {
        fun onCheckedAnswer(isCorrect: Boolean)
    }
}
