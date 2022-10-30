/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.ui

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import dev.sertan.android.ram.coreui.R
import dev.sertan.android.ram.coreui.databinding.LayoutColorBinding

class ColorItemViewHolder(
    private val binding: LayoutColorBinding,
    private val listener: OnColorClickedListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(color: Int): Unit = with(binding.colorView) {
        background = AppCompatResources.getDrawable(itemView.context, R.drawable.bg_drawing_colors)
            ?.apply { setTint(color) }
        setOnClickListener { listener?.onColorClicked(color) }
    }

    fun interface OnColorClickedListener {
        fun onColorClicked(color: Int)
    }
}
