/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.objectrecognition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.core.domain.usecase.SpeechToTextUseCase
import dev.sertan.android.ram.feature.material.domain.usecase.GetMaterialsUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class ObjectRecognitionViewModel @Inject constructor(
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val speechToTextUseCase: SpeechToTextUseCase
) : ViewModel() {

    private val materialIndex = MutableStateFlow(0)

    var listener: MicListener? = null

    val uiState: StateFlow<ObjectRecognitionUiState> = flow {
        val materials = getMaterialsUseCase()
        materialIndex.collect {
            emit(ObjectRecognitionUiState.getState(materials = materials, index = it))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ObjectRecognitionUiState.initialState()
    )

    fun listenMic() {
        listener?.onStart()
        speechToTextUseCase(
            onComplete = { input ->
                val material = uiState.value.material ?: return@speechToTextUseCase
                if (material.description.lowercase() == input) {
                    listener?.onCorrect()
                    goToNextObject()
                } else {
                    listener?.onWrong()
                }
                listener?.onComplete()
            },
            onError = { errorCode -> listener?.onError(errorCode) }
        )
    }

    fun goToNextObject() {
        materialIndex.update { it.inc() }
    }

    fun goToPreviousObject() {
        materialIndex.update { it.dec() }
    }

    interface MicListener {
        fun onStart()
        fun onCorrect()
        fun onWrong()
        fun onComplete()
        fun onError(errorCode: Int)
    }
}
