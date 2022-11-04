/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.util

import android.content.res.ColorStateList
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.ColorInt
import com.google.android.material.button.MaterialButton

fun MaterialButton.setIconTint(@ColorInt color: Int) {
    iconTint = ColorStateList.valueOf(color)
}

fun ImageView.loadFromUrl(uri: Uri?) {
    GlideApp.with(this).load(uri).into(this)
}