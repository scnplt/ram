/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.sertan.android.ram.coreui.databinding.LayoutColorBinding
import dev.sertan.android.ram.coreui.ui.ColorItemViewHolder.OnColorClickedListener

internal class ColorItemAdapter : RecyclerView.Adapter<ColorItemViewHolder>() {

    private var colors: MutableList<Int> = mutableListOf()

    var listener: OnColorClickedListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newColors: List<Int>) {
        colors.addAll(newColors)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorItemViewHolder {
        val binding = LayoutColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ColorItemViewHolder, position: Int) {
        holder.bind(colors[position])
    }

    override fun getItemCount(): Int = colors.size
}
