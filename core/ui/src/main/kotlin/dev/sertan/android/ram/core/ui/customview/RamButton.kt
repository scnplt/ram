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
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import dev.sertan.android.ram.core.ui.R
import dev.sertan.android.ram.core.ui.databinding.CustomButtonBinding
import dev.sertan.android.ram.core.ui.util.Constants.NO_RESOURCE
import dev.sertan.android.ram.core.ui.util.extension.viewBinding

class RamButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = viewBinding(CustomButtonBinding::inflate)

    var buttonText: String
        get() = binding.textView.text.toString()
        set(value) {
            binding.textView.text = value
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.RamButton).apply {
            buttonText = getText(R.styleable.RamButton_buttonText).toString()
            getResourceId(R.styleable.RamButton_buttonIcon, NO_RESOURCE)
                .also { iconResId -> if (iconResId != NO_RESOURCE) setButtonIcon(iconResId) }
        }.recycle()
        setBackgroundResource(R.drawable.bg_button)
    }

    fun setButtonIcon(@DrawableRes iconResId: Int) {
        binding.buttonIconImageView.setImageResource(iconResId)
    }

    fun setButtonText(@StringRes textResId: Int) {
        binding.textView.setText(textResId)
    }
}
