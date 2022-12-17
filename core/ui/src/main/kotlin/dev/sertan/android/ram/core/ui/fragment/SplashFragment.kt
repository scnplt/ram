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
import dev.sertan.android.ram.core.ui.R
import dev.sertan.android.ram.core.ui.RamActivity
import dev.sertan.android.ram.core.ui.databinding.FragmentSplashBinding
import dev.sertan.android.ram.core.ui.util.extension.viewBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? RamActivity)?.let { binding.appNameTextView.text = it.label }
    }

    companion object {
        const val DEFAULT_DURATION_MS = 3000L
    }
}