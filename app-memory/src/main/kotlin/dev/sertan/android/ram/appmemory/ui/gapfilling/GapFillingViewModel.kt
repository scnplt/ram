/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.gapfilling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.appmemory.domain.usecase.GetGapFillingQuestionsUseCase
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import dev.sertan.android.ram.feature.material.ui.Material
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class GapFillingViewModel @Inject constructor(
    getGapFillingQuestionsUseCase: GetGapFillingQuestionsUseCase,
    private val textToSpeechUseCase: TextToSpeechUseCase
) : ViewModel() {

    var isValidationActive = true
    private val questionIndex = MutableStateFlow(0)

    val uiState: StateFlow<GapFillingUiState> = flow {
        val questions = getGapFillingQuestionsUseCase()
        questionIndex.collect { index ->
            val question = questions.getOrNull(index)
            emit(
                GapFillingUiState(
                    gapFillingQuestion = question,
                    isForwardButtonVisible = index in 0 until questions.lastIndex,
                    isFinishButtonVisible = index == questions.lastIndex,
                    isEmptyListMessageVisible = questions.isEmpty()
                )
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = GapFillingUiState.initialState()
    )

    fun goToNextQuestion() {
        questionIndex.update { it.inc() }
        isValidationActive = true
    }

    fun speakCurrentQuestionContent(): Unit =
        textToSpeechUseCase.speak(uiState.value.gapFillingQuestion?.content)

    fun isMaterialCorrect(material: Material): Boolean? = uiState.takeIf { isValidationActive }
        ?.value?.gapFillingQuestion?.correctMaterialUid?.equals(material.uid)

    fun stopSpeech(): Unit = textToSpeechUseCase.stopSpeech()
}
