/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.drawing

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.sertan.android.ram.core.ui.util.extension.getColorList
import dev.sertan.android.ram.core.ui.util.extension.setIconTint
import dev.sertan.android.ram.core.ui.util.extension.viewBinding
import dev.sertan.android.ram.feature.drawing.databinding.FragmentDrawingBinding
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class DrawingFragment :
    Fragment(R.layout.fragment_drawing),
    ColorItemViewHolder.OnColorClickedListener,
    DrawingView.Listener {

    private val binding by viewBinding(FragmentDrawingBinding::bind)
    private val viewModel by viewModels<DrawingViewModel>()
    private lateinit var adapter: ColorItemAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ColorItemAdapter().apply { listener = this@DrawingFragment }
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
        deleteAllButton.setOnClickListener { showClearCanvasDialog() }
        eraserButton.setOnClickListener { viewModel.setToErasingMode() }
        brushButton.setOnClickListener { viewModel.setToDrawingMode() }
    }

    private fun showClearCanvasDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setCancelable(true)
            setMessage(R.string.do_you_want_clear_canvas)
            setNegativeButton(R.string.no) { dialog, _ -> dialog.cancel() }
            setPositiveButton(R.string.yes) { _, _ -> binding.drawingView.clearCanvas() }
        }.show()
    }
}