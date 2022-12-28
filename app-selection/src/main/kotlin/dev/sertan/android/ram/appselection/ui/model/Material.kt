/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.ui.model

import androidx.recyclerview.widget.DiffUtil

data class Material(
    val uid: String,
    val description: String,
    val mediaUrl: String,
    val attribution: String?
)

internal object MaterialDiffUtil : DiffUtil.ItemCallback<Material>() {
    override fun areItemsTheSame(old: Material, new: Material): Boolean = old.uid == new.uid
    override fun areContentsTheSame(old: Material, new: Material): Boolean = old == new
}
