/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.ui.practice.adapter

import androidx.recyclerview.widget.RecyclerView
import dev.sertan.android.ram.appselection.databinding.ItemQuestionMaterialBinding
import dev.sertan.android.ram.core.ui.util.loadFromUrl
import dev.sertan.android.ram.core.ui.util.show
import dev.sertan.android.ram.feature.material.ui.model.Material

class QuestionViewHolder(
    private val binding: ItemQuestionMaterialBinding,
    private val listener: Listener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(material: Material): Unit = with(binding) {
        materialImageView.loadFromUrl(material.mediaUrl)
        materialConstraintLayout.setOnClickListener {
            if (listener == null) return@setOnClickListener
            with(listener) {
                isMaterialCorrect(material)?.let {
                    val icon = if (it) {
                        onCorrectMaterialClicked(material)
                        dev.sertan.android.ram.core.ui.R.drawable.ic_check_circle
                    } else {
                        onWrongMaterialClicked(material)
                        dev.sertan.android.ram.core.ui.R.drawable.ic_outline_cancel
                    }
                    answerStateImageView.setImageResource(icon)
                    answerStateImageView.show()
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
