/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.ui.model

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class Material(
    val uid: String,
    val description: String,
    val mediaUri: Uri?,
    val attribution: String?
)

object MaterialDiffUtil : DiffUtil.ItemCallback<Material>() {

    override fun areItemsTheSame(oldItem: Material, newItem: Material): Boolean =
        oldItem.uid == newItem.uid

    override fun areContentsTheSame(oldItem: Material, newItem: Material): Boolean =
        oldItem == newItem
}
