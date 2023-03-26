/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.core.ui.R
import dev.sertan.android.ram.core.ui.RamActivity
import dev.sertan.android.ram.core.ui.databinding.FragmentSplashBinding
import dev.sertan.android.ram.core.ui.util.appNo
import dev.sertan.android.ram.core.ui.util.labelWithoutPrefix
import dev.sertan.android.ram.core.ui.util.viewBinding
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)

    @Inject
    lateinit var locale: Locale

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            appNoTextView.text = getString(R.string.app_no, context?.appNo)
            appNameTextView.text = context?.labelWithoutPrefix?.uppercase(locale)
            (activity as? RamActivity)?.run { appImageView.setImageResource(appImageResId) }
        }
    }

    companion object {
        const val DEFAULT_DURATION_MS = 3000L
    }
}
