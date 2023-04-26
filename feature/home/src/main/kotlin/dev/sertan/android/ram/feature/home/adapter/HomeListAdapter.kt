/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import dev.sertan.android.ram.core.ui.adapter.BaseListAdapter
import dev.sertan.android.ram.core.ui.adapter.BaseViewHolder
import dev.sertan.android.ram.feature.home.adapter.viewholder.ButtonItemViewHolder
import dev.sertan.android.ram.feature.home.adapter.viewholder.HeaderItemViewHolder
import dev.sertan.android.ram.feature.home.adapter.viewholder.TitleItemViewHolder
import dev.sertan.android.ram.feature.home.databinding.ListItemButtonBinding
import dev.sertan.android.ram.feature.home.databinding.ListItemHeaderBinding
import dev.sertan.android.ram.feature.home.databinding.ListItemTitleBinding
import javax.inject.Inject

class HomeListAdapter @Inject constructor() : BaseListAdapter<HomeListItem>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<HomeListItem> = when (viewType) {
        HomeListItem.ItemType.TITLE.ordinal -> getTitleViewHolder(parent)
        HomeListItem.ItemType.HEADER.ordinal -> getHeaderViewHolder(parent)
        HomeListItem.ItemType.SCREEN_BUTTON.ordinal -> getScreenButtonViewHolder(parent)
        else -> throw IllegalArgumentException("Unknown viewType: $viewType")
    }

    private fun getTitleViewHolder(parent: ViewGroup): BaseViewHolder<HomeListItem> {
        val binding =
            ListItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleItemViewHolder(binding)
    }

    private fun getHeaderViewHolder(parent: ViewGroup): BaseViewHolder<HomeListItem> {
        val binding =
            ListItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderItemViewHolder(binding)
    }

    private fun getScreenButtonViewHolder(parent: ViewGroup): BaseViewHolder<HomeListItem> {
        val binding =
            ListItemButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ButtonItemViewHolder(binding)
    }
}
