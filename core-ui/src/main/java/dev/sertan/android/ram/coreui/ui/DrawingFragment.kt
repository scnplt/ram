/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.coreui.R
import dev.sertan.android.ram.coreui.databinding.FragmentDrawingBinding
import dev.sertan.android.ram.coreui.ui.ColorItemViewHolder.OnColorClickedListener
import dev.sertan.android.ram.coreui.ui.customview.DrawingView
import dev.sertan.android.ram.coreui.util.getColorList
import dev.sertan.android.ram.coreui.util.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class DrawingFragment : Fragment(R.layout.fragment_drawing) {
    private val binding by viewBinding(FragmentDrawingBinding::bind)

    @Inject
    lateinit var adapter: ColorItemAdapter

    private val drawingViewListener = object : DrawingView.Listener {
        override fun onPenColorChanged(color: Int) {
            binding.brushButton.iconTint = ColorStateList.valueOf(color)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.listener = OnColorClickedListener { binding.drawingView.penColor = it }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
        adapter.submitList(newColors = resources.getColorList(R.array.colors))
    }

    private fun setUpComponents() = with(binding) {
        drawingView.listener = drawingViewListener
        deleteAllButton.setOnClickListener { drawingView.clearCanvas() }
        eraserButton.setOnClickListener { drawingView.currentMode = DrawingView.Mode.ERASE }
        brushButton.setOnClickListener { drawingView.currentMode = DrawingView.Mode.DRAW }
        brushButton.iconTint = ColorStateList.valueOf(drawingView.penColor)
        colorPaletteRecyclerView.adapter = adapter
    }
}
