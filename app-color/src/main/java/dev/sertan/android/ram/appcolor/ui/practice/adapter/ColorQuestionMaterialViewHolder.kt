/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.ui.practice.adapter

import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import dev.sertan.android.ram.appcolor.databinding.ItemColorQuestionMaterialBinding
import dev.sertan.android.ram.core.model.ui.Material
import dev.sertan.android.ram.core.ui.util.extension.loadFromUrl

internal class ColorQuestionMaterialViewHolder(
    private val binding: ItemColorQuestionMaterialBinding,
    private val listener: Listener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(material: Material) = with(binding) {
        materialImageView.loadFromUrl(material.mediaUrl)
        materialCardView.setOnClickListener {
            if (listener == null) return@setOnClickListener
            with(listener) {
                isMaterialCorrect(material)?.let {
                    if (it) onCorrectMaterialClicked(material) else onWrongMaterialClicked(material)
                    correctImageView.isInvisible = !it
                    wrongImageView.isInvisible = it
                }
                onMaterialClicked(material)
            }
        }
    }

    interface Listener {
        fun onMaterialClicked(material: Material)
        fun isMaterialCorrect(material: Material): Boolean?
        fun onCorrectMaterialClicked(material: Material)
        fun onWrongMaterialClicked(material: Material)
    }
}
