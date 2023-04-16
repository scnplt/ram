/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.customview

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import dev.sertan.android.ram.core.ui.R
import dev.sertan.android.ram.core.ui.databinding.CustomImageButtonBinding
import dev.sertan.android.ram.core.ui.util.viewBinding

class RamImageButton(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = viewBinding(CustomImageButtonBinding::inflate)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.RamImageButton).apply {
            getColor(R.styleable.RamImageButton_tint, NO_RESOURCE)
                .also { colorInt -> if (colorInt != NO_RESOURCE) setIconTint(colorInt) }
            getResourceId(R.styleable.RamImageButton_icon, NO_RESOURCE)
                .also { iconResId -> if (iconResId != NO_RESOURCE) setIcon(iconResId) }
        }.recycle()
    }

    fun setIconTint(@ColorInt colorInt: Int): Unit = binding.iconImageView.setColorFilter(colorInt)

    fun setIcon(@DrawableRes iconResId: Int): Unit =
        binding.iconImageView.setImageResource(iconResId)
}
