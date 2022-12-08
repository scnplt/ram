/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.ui.practice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import dev.sertan.android.ram.appcolor.databinding.ItemColorQuestionMaterialBinding
import dev.sertan.android.ram.core.model.ui.Material
import javax.inject.Inject

internal class ColorQuestionMaterialAdapter @Inject constructor() :
    ListAdapter<Material, ColorQuestionMaterialViewHolder>(ColorMaterialDiffUtil) {

    var listener: ColorQuestionMaterialViewHolder.Listener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ColorQuestionMaterialViewHolder {
        val binding = ItemColorQuestionMaterialBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ColorQuestionMaterialViewHolder(binding = binding, listener = listener)
    }

    override fun onBindViewHolder(holder: ColorQuestionMaterialViewHolder, position: Int): Unit =
        holder.bind(getItem(position))
}
