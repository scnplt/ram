/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.matching.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import dev.sertan.android.ram.appmemory.databinding.ListItemMaterialBinding
import dev.sertan.android.ram.appmemory.databinding.ListItemTitleBinding
import dev.sertan.android.ram.appmemory.ui.matching.adapter.viewholder.MatchingMaterialViewHolder
import dev.sertan.android.ram.appmemory.ui.matching.adapter.viewholder.MatchingTitleViewHolder
import dev.sertan.android.ram.core.ui.adapter.BaseListAdapter
import dev.sertan.android.ram.core.ui.adapter.BaseViewHolder
import javax.inject.Inject

internal class MatchingListAdapter @Inject constructor() : BaseListAdapter<MatchingListItem>() {

    var listener: MaterialItemListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MatchingListItem> = when (viewType) {
        MatchingListItem.ItemType.TITLE.ordinal -> getTitleViewHolder(parent)
        MatchingListItem.ItemType.MATERIAL.ordinal -> getMaterialViewHolder(parent)
        else -> throw IllegalArgumentException("Unknown viewType: $viewType")
    }

    private fun getTitleViewHolder(parent: ViewGroup): BaseViewHolder<MatchingListItem> {
        val binding =
            ListItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchingTitleViewHolder(binding)
    }

    private fun getMaterialViewHolder(parent: ViewGroup): BaseViewHolder<MatchingListItem> {
        val binding =
            ListItemMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchingMaterialViewHolder(binding, listener)
    }
}
