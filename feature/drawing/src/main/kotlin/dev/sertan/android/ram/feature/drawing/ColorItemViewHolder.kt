/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.drawing

import android.content.res.ColorStateList
import androidx.recyclerview.widget.RecyclerView
import dev.sertan.android.ram.feature.drawing.databinding.ItemLayoutColorBinding

class ColorItemViewHolder(
    private val binding: ItemLayoutColorBinding,
    private val listener: OnColorClickedListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(color: Int): Unit = with(binding.colorView) {
        backgroundTintList = ColorStateList.valueOf(color)
        setOnClickListener { listener?.onColorClicked(color) }
    }

    fun interface OnColorClickedListener {
        fun onColorClicked(color: Int)
    }
}
