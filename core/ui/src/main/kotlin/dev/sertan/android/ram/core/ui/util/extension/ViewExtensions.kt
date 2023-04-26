/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.util.extension

import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import dev.sertan.android.ram.core.ui.util.GlideApp

fun View.show() {
    if (visibility != View.VISIBLE) isVisible = true
}

fun View.hide() {
    if (visibility == View.VISIBLE) isVisible = false
}

fun ImageButton.setIconTint(@ColorInt color: Int) {
    setColorFilter(color, PorterDuff.Mode.SRC_IN)
}

fun ImageView.loadFromUrl(url: String?) {
    GlideApp.with(this).load(url).into(this)
}

@Suppress("MagicNumber")
fun RatingBar.setRatingByPercent(percent: Float) {
    rating = percent * numStars / 100
}
