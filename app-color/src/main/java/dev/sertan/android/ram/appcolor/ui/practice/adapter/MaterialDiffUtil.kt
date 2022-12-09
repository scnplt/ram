/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.ui.practice.adapter

import androidx.recyclerview.widget.DiffUtil
import dev.sertan.android.ram.core.model.ui.Material

internal object MaterialDiffUtil : DiffUtil.ItemCallback<Material>() {

    override fun areItemsTheSame(oldItem: Material, newItem: Material): Boolean =
        oldItem.uid == newItem.uid

    override fun areContentsTheSame(oldItem: Material, newItem: Material): Boolean =
        oldItem == newItem
}
