/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.home.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.sertan.android.ram.core.ui.adapter.BaseListItem

sealed class HomeListItem : BaseListItem<HomeListItem> {

    enum class ItemType { TITLE, HEADER, SCREEN_BUTTON }

    data class TitleItem(val title: String?) : HomeListItem() {

        override val viewType: Int = ItemType.TITLE.ordinal

        override fun areItemsTheSame(newItem: HomeListItem): Boolean =
            newItem is TitleItem && newItem.title == title

        override fun areContentsTheSame(newItem: HomeListItem): Boolean =
            newItem is TitleItem && newItem == this
    }

    data class HeaderItem(@DrawableRes val iconResId: Int) : HomeListItem() {

        override val viewType: Int = ItemType.HEADER.ordinal

        override fun areItemsTheSame(newItem: HomeListItem): Boolean =
            newItem is HeaderItem && newItem.iconResId == iconResId

        override fun areContentsTheSame(newItem: HomeListItem): Boolean =
            newItem is HeaderItem && newItem == this
    }

    data class ButtonItem(
        @StringRes val buttonTextResId: Int,
        @DrawableRes val buttonIconResId: Int,
        val onClicked: () -> Unit
    ) : HomeListItem() {

        override val viewType: Int = ItemType.SCREEN_BUTTON.ordinal

        override fun areItemsTheSame(newItem: HomeListItem): Boolean =
            newItem is ButtonItem && newItem.buttonIconResId == buttonIconResId

        override fun areContentsTheSame(newItem: HomeListItem): Boolean =
            newItem is ButtonItem && newItem == this
    }
}
