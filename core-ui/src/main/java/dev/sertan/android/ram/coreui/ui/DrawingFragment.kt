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
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import dev.sertan.android.ram.coreui.R
import dev.sertan.android.ram.coreui.databinding.FragmentDrawingBinding
import dev.sertan.android.ram.coreui.ui.ColorItemViewHolder.OnColorClickedListener
import dev.sertan.android.ram.coreui.ui.customview.DrawingView
import dev.sertan.android.ram.coreui.util.viewBinding

class DrawingFragment : Fragment(R.layout.fragment_drawing) {
    private val binding by viewBinding(FragmentDrawingBinding::bind)

    private val adapter by lazy {
        ColorItemAdapter().apply {
            listener = OnColorClickedListener { binding.drawingView.penColor = it }
        }
    }

    private val drawingViewListener = object : DrawingView.Listener {
        override fun onPenColorChanged(color: Int) {
            binding.penColorButton.iconTint = ColorStateList.valueOf(color)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            penColorButton.setOnClickListener {
                drawingView.currentMode = DrawingView.Mode.DRAW
                colorPalette.isInvisible = false
            }
            eraseButton.setOnClickListener {
                drawingView.currentMode = DrawingView.Mode.ERASE
                colorPalette.isInvisible = true
            }
            drawingView.listener = drawingViewListener
            colorPalette.adapter = adapter
            penColorButton.iconTint = ColorStateList.valueOf(drawingView.penColor)
        }
        setUpColorPalette()
    }

    private fun setUpColorPalette() {
        val colorArrayRes = resources.obtainTypedArray(R.array.colors)
        val colors =
            (0 until colorArrayRes.length()).map { colorArrayRes.getColor(it, -1) }.toList()
        adapter.submitList(newColors = colors)
        colorArrayRes.recycle()
    }
}
