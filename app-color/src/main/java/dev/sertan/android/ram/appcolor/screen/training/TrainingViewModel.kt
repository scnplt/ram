/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.coredomain.usecase.GetMaterialsUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class TrainingViewModel @Inject constructor(
    private val getMaterialsUseCase: GetMaterialsUseCase
) : ViewModel() {

    private val materialIndex = MutableStateFlow(0)

    private val _uiState = MutableStateFlow(TrainingUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val materials = getMaterialsUseCase().also { if (it.isEmpty()) return@launch }
            materialIndex.collect { index ->
                _uiState.update {
                    it.copy(
                        material = materials[index],
                        isBackButtonVisible = index > 0,
                        isForwardButtonVisible = index in 0 until materials.lastIndex,
                        isFinishButtonVisible = index == materials.lastIndex
                    )
                }
            }
        }
    }

    fun goToNextMaterial(): Unit = materialIndex.update { it.inc() }

    fun goToPreviousMaterial(): Unit = materialIndex.update { it.dec() }
}
