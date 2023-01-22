/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.question.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import dev.sertan.android.ram.core.ui.R
import dev.sertan.android.ram.core.ui.util.loadFromUrl
import dev.sertan.android.ram.core.ui.util.show
import dev.sertan.android.ram.feature.question.databinding.ItemQuestionMaterialBinding
import dev.sertan.android.ram.feature.question.ui.model.Material

class QuestionMaterialViewHolder(
    private val binding: ItemQuestionMaterialBinding,
    private val listener: MaterialListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(material: Material): Unit = with(binding) {
        materialImageView.loadFromUrl(material.mediaUrl)
        materialConstraintLayout.setOnClickListener {
            val isCorrect =
                listener?.run { isMaterialCorrect(material) } ?: return@setOnClickListener
            val icon = if (isCorrect) R.drawable.ic_check_circle else R.drawable.ic_outline_cancel
            listener.onMaterialClicked(material, isCorrect = isCorrect)
            answerStateImageView.setImageResource(icon)
            answerStateImageView.show()
        }
    }

    interface MaterialListener {
        fun onMaterialClicked(material: Material, isCorrect: Boolean)
        fun isMaterialCorrect(material: Material): Boolean?
    }
}
