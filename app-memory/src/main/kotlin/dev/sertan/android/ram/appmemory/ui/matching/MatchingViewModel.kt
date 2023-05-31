/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.matching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.appmemory.domain.usecase.MatchingQuestionsUseCase
import dev.sertan.android.ram.appmemory.ui.matching.adapter.MatchingListItem
import dev.sertan.android.ram.appmemory.ui.matching.adapter.MatchingListItem.MaterialItem
import dev.sertan.android.ram.appmemory.ui.matching.adapter.MatchingListItem.TitleItem
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
internal class MatchingViewModel @Inject constructor(
    getMatchingQuestionsUseCase: MatchingQuestionsUseCase,
    private val textToSpeechUseCase: TextToSpeechUseCase
) : ViewModel() {

    private val questionIndex = MutableStateFlow(0)
    private var lastSelectedMaterialUid: String? = null
    var isValidationActive = true

    val uiState: StateFlow<MatchingUiState> = flow {
        val questions = getMatchingQuestionsUseCase()
        if (questions.isEmpty()) {
            emit(MatchingUiState.initialState().copy(isEmptyListMessageVisible = true))
            return@flow
        }
        questionIndex.collect { index ->
            val contentItems = arrayListOf<MatchingListItem>()
            questions.getOrNull(index)?.let { question ->
                contentItems.add(TitleItem(titleText = question.content))
                question.materials.forEach { material -> contentItems.add(MaterialItem(material)) }
            }

            emit(
                MatchingUiState(
                    contentItems = contentItems,
                    isNextButtonInvisible = index !in 0 until questions.lastIndex,
                    isFinishButtonVisible = index == questions.lastIndex,
                    isEmptyListMessageVisible = null
                )
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = MatchingUiState.initialState()
    )

    fun goToNextQuestion() {
        questionIndex.update { it.inc() }
        lastSelectedMaterialUid = null
        isValidationActive = true
    }

    fun checkAnswerState(selectedMaterial: Material): Boolean? {
        if (lastSelectedMaterialUid == null || !isValidationActive) {
            lastSelectedMaterialUid = selectedMaterial.uid
            return null
        }
        return lastSelectedMaterialUid == selectedMaterial.uid
    }

    fun stopSpeech(): Unit = textToSpeechUseCase.stopSpeech()
}
