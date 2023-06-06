/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.customview

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import dev.sertan.android.ram.appcommunication.R
import dev.sertan.android.ram.appcommunication.databinding.CustomButtonCatchBinding
import dev.sertan.android.ram.core.ui.util.Constants.NO_RESOURCE
import dev.sertan.android.ram.core.ui.util.extension.viewBinding

class RamCatchButton(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = viewBinding(CustomButtonCatchBinding::inflate)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.RamCatchButton).apply {
            getColor(R.styleable.RamCatchButton_tint, NO_RESOURCE)
                .also { colorInt -> if (colorInt != NO_RESOURCE) setIconTint(colorInt) }
            getResourceId(R.styleable.RamCatchButton_icon, NO_RESOURCE)
                .also { iconResId -> if (iconResId != NO_RESOURCE) setIcon(iconResId) }
        }.recycle()
    }

    fun setIconTint(@ColorInt colorInt: Int): Unit = binding.iconImageView.setColorFilter(colorInt)

    fun setIcon(@DrawableRes iconResId: Int): Unit =
        binding.iconImageView.setImageResource(iconResId)
}
