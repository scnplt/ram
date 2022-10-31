/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.ui.drawing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.coreui.R
import dev.sertan.android.ram.coreui.databinding.FragmentDrawingBinding
import dev.sertan.android.ram.coreui.ui.ColorItemAdapter
import dev.sertan.android.ram.coreui.ui.ColorItemViewHolder
import dev.sertan.android.ram.coreui.util.getColorList
import dev.sertan.android.ram.coreui.util.setIconTint
import dev.sertan.android.ram.coreui.util.viewBinding
import javax.inject.Inject
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DrawingFragment :
    Fragment(R.layout.fragment_drawing),
    ColorItemViewHolder.OnColorClickedListener,
    DrawingView.Listener {

    private val binding by viewBinding(FragmentDrawingBinding::bind)
    private val viewModel by viewModels<DrawingViewModel>()

    @Inject
    lateinit var adapter: ColorItemAdapter

    private val uiStateFlowCollector = FlowCollector<DrawingUiState> { uiState ->
        with(binding) {
            brushButton.isActivated = uiState.isBrushButtonActive
            eraserButton.isActivated = !uiState.isBrushButtonActive
            drawingView.currentMode = uiState.drawingViewMode
        }
    }

    override fun onColorClicked(color: Int) {
        binding.drawingView.penColor = color
    }

    override fun onPenColorChanged(color: Int): Unit = binding.brushButton.setIconTint(color)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.listener = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(uiStateFlowCollector)
            }
        }
        setUpComponents()
        onPenColorChanged(color = binding.drawingView.penColor)
    }

    private fun setUpComponents(): Unit = with(binding) {
        drawingView.listener = this@DrawingFragment
        colorPaletteRecyclerView.adapter = adapter
        adapter.submitList(newColors = resources.getColorList(R.array.palette_colors))
        deleteAllButton.setOnClickListener { drawingView.clearCanvas() }
        eraserButton.setOnClickListener { viewModel.setToErasingMode() }
        brushButton.setOnClickListener { viewModel.setToDrawingMode() }
    }
}
