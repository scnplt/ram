/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.matching.adapter

import dev.sertan.android.ram.core.ui.adapter.BaseListItem
import dev.sertan.android.ram.feature.material.ui.Material

internal sealed class MatchingListItem : BaseListItem<MatchingListItem> {

    enum class ItemType { TITLE, MATERIAL }

    data class TitleItem(val titleText: String) : MatchingListItem() {

        override val viewType: Int = ItemType.TITLE.ordinal

        override fun areItemsTheSame(newItem: MatchingListItem): Boolean =
            newItem is TitleItem && newItem.titleText == titleText

        override fun areContentsTheSame(newItem: MatchingListItem): Boolean =
            newItem is TitleItem && newItem == this
    }

    data class MaterialItem(val material: Material) : MatchingListItem() {

        override val viewType: Int = ItemType.MATERIAL.ordinal

        override fun areItemsTheSame(newItem: MatchingListItem): Boolean =
            newItem is MaterialItem && newItem.material == material

        override fun areContentsTheSame(newItem: MatchingListItem): Boolean =
            newItem is MaterialItem && newItem == this
    }
}
