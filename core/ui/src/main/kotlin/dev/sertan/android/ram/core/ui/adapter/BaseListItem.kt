/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.adapter

interface BaseListItem<T : Any> {

    val viewType: Int

    fun areItemsTheSame(newItem: T): Boolean

    fun areContentsTheSame(newItem: T): Boolean
}
