/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.adapter

import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<I : BaseListItem<I>> :
    ListAdapter<I, BaseViewHolder<I>>(BaseListItemDiffUtil()) {

    override fun getItemViewType(position: Int): Int = getItem(position).viewType

    override fun onBindViewHolder(holder: BaseViewHolder<I>, position: Int): Unit =
        holder.bind(getItem(position))
}
