/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.voiceimitation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.core.domain.usecase.GetApplicationConfigsUseCase
import dev.sertan.android.ram.core.domain.usecase.SpeechToTextUseCase
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

private const val SOUNDS_KEY = "sounds_to_repeat"
private const val LETTER_DELIMITER = ','

@HiltViewModel
internal class VoiceImitationViewModel @Inject constructor(
    getApplicationConfigsUseCase: GetApplicationConfigsUseCase,
    private val textToSpeechUseCase: TextToSpeechUseCase,
    private val speechToTextUseCase: SpeechToTextUseCase
) : ViewModel() {

    private val soundIndex = MutableStateFlow(0)
    private val sounds = getApplicationConfigsUseCase(key = SOUNDS_KEY).split(LETTER_DELIMITER)

    var listener: MicListener? = null

    val uiState: StateFlow<VoiceImitationUiState> = soundIndex.map { index ->
        val nextSound = sounds.getOrNull(index)
        textToSpeechUseCase.speak(message = nextSound)

        VoiceImitationUiState(
            sound = nextSound,
            isForwardButtonVisible = index < sounds.lastIndex,
            isFinishButtonVisible = index == sounds.lastIndex,
            isBackButtonInvisible = index == 0
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = VoiceImitationUiState.initialState()
    )

    fun listenMic() {
        listener?.onStart()
        speechToTextUseCase(
            onComplete = { input ->
                if (uiState.value.sound == input) listener?.onCorrect() else listener?.onWrong()
                listener?.onComplete()
            },
            onError = { errorCode -> listener?.onError(errorCode) }
        )
    }

    fun goToNextSound() {
        soundIndex.update { it.inc() }
    }

    fun goToPreviousSound() {
        soundIndex.update { it.dec() }
    }

    interface MicListener {
        fun onStart()
        fun onCorrect()
        fun onWrong()
        fun onComplete()
        fun onError(errorCode: Int)
    }
}
