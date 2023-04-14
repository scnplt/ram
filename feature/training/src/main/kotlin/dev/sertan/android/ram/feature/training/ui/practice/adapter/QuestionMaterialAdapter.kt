/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training.ui.practice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import dev.sertan.android.ram.feature.material.ui.Material
import dev.sertan.android.ram.feature.training.databinding.ItemQuestionMaterialBinding
import javax.inject.Inject

class QuestionMaterialAdapter @Inject constructor() :
    ListAdapter<Material, QuestionMaterialViewHolder>(MaterialDiffUtil) {

    var listener: QuestionMaterialViewHolder.MaterialListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionMaterialViewHolder {
        val binding =
            ItemQuestionMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionMaterialViewHolder(binding = binding, listener = listener)
    }

    override fun onBindViewHolder(holder: QuestionMaterialViewHolder, position: Int): Unit =
        holder.bind(getItem(position))
}
