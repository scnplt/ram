/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.matching.adapter.viewholder

import dev.sertan.android.ram.appmemory.databinding.ListItemTitleBinding
import dev.sertan.android.ram.appmemory.ui.matching.adapter.MatchingListItem
import dev.sertan.android.ram.appmemory.ui.matching.adapter.MatchingListItem.TitleItem
import dev.sertan.android.ram.core.ui.adapter.BaseViewHolder

internal class MatchingTitleViewHolder(
    private val binding: ListItemTitleBinding
) : BaseViewHolder<MatchingListItem>(binding) {

    override fun bind(item: MatchingListItem) {
        if (item !is TitleItem) return
        binding.titleTextView.text = item.titleText
    }
}
