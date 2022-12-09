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
import dev.sertan.android.ram.appcolor.databinding.ItemQuestionMaterialBinding
import dev.sertan.android.ram.core.model.ui.Material
import javax.inject.Inject

internal class QuestionAdapter @Inject constructor() :
    ListAdapter<Material, QuestionViewHolder>(MaterialDiffUtil) {

    var listener: QuestionViewHolder.Listener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionViewHolder {
        val binding =
            ItemQuestionMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding = binding, listener = listener)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int): Unit =
        holder.bind(getItem(position))
}
