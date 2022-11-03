/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.sertan.android.ram.coreui.R
import dev.sertan.android.ram.coreui.databinding.FragmentSplashBinding
import dev.sertan.android.ram.coreui.util.viewBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appNameTextView.text =
            with(requireContext()) { applicationInfo.loadLabel(packageManager) }
    }
}
