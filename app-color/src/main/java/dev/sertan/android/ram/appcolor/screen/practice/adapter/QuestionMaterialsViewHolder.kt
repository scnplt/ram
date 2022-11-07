/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.practice.adapter

import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import dev.sertan.android.ram.appcolor.databinding.ItemQuestionMaterialBinding
import dev.sertan.android.ram.coreui.model.Material
import dev.sertan.android.ram.coreui.util.extension.loadFromUrl

internal class QuestionMaterialsViewHolder(
    private val binding: ItemQuestionMaterialBinding,
    private val listener: QuestionMaterialListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(material: Material) = with(binding) {
        materialImageView.loadFromUrl(material.mediaUri)
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

    interface QuestionMaterialListener {
        fun onMaterialClicked(material: Material)
        fun isMaterialCorrect(material: Material): Boolean?
        fun onCorrectMaterialClicked(material: Material)
        fun onWrongMaterialClicked(material: Material)
    }
}
