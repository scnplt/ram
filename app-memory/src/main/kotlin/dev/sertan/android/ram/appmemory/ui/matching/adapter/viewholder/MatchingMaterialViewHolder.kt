/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.matching.adapter.viewholder

import androidx.core.view.isVisible
import dev.sertan.android.ram.appmemory.databinding.ListItemMaterialBinding
import dev.sertan.android.ram.appmemory.ui.matching.adapter.MatchingListItem
import dev.sertan.android.ram.appmemory.ui.matching.adapter.MatchingListItem.MaterialItem
import dev.sertan.android.ram.appmemory.ui.matching.adapter.MaterialItemListener
import dev.sertan.android.ram.core.ui.adapter.BaseViewHolder
import dev.sertan.android.ram.core.ui.util.extension.loadFromUrl

internal class MatchingMaterialViewHolder(
    private val binding: ListItemMaterialBinding,
    private val materialListener: MaterialItemListener?
) : BaseViewHolder<MatchingListItem>(binding) {

    override fun bind(item: MatchingListItem) {
        if (item !is MaterialItem) return
        with(binding) {
            materialImageView.loadFromUrl(item.material.mediaUrl)
            selectedStateImageView.isVisible = false
            materialImageView.setOnClickListener {
                materialListener?.onMaterialClicked(material = item.material)
                selectedStateImageView.isVisible = true
                materialImageView.setOnClickListener(null)
            }
        }
    }
}
