/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.home.adapter.viewholder

import dev.sertan.android.ram.core.ui.adapter.BaseViewHolder
import dev.sertan.android.ram.feature.home.adapter.HomeListItem
import dev.sertan.android.ram.feature.home.databinding.ListItemTitleBinding

internal class TitleItemViewHolder(
    private val binding: ListItemTitleBinding
) : BaseViewHolder<HomeListItem>(binding) {

    override fun bind(item: HomeListItem) {
        if (item !is HomeListItem.TitleItem) return
        binding.titleTextView.text = item.title
    }
}
