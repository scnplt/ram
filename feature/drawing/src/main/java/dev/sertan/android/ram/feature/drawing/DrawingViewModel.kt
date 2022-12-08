/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.drawing

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DrawingViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(DrawingUiState())
    val uiState get() = _uiState.asStateFlow()

    fun setToDrawingMode(): Unit = _uiState.update {
        it.copy(isBrushButtonActive = true, drawingViewMode = DrawingView.Mode.DRAW)
    }

    fun setToErasingMode(): Unit = _uiState.update {
        it.copy(isBrushButtonActive = false, drawingViewMode = DrawingView.Mode.ERASE)
    }
}
