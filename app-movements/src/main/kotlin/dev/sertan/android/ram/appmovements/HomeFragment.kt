/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appmovements.HomeFragmentDirections.Companion.actionHomeFragmentToPoseDetectionFragment
import dev.sertan.android.ram.appmovements.databinding.FragmentHomeBinding
import dev.sertan.android.ram.core.ui.fragment.texttospeechprovider.TextToSpeechProviderFragment
import dev.sertan.android.ram.core.ui.util.navTo
import dev.sertan.android.ram.core.ui.util.viewBinding

@AndroidEntryPoint
internal class HomeFragment : TextToSpeechProviderFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onTextToSpeechStateChanged(isActive: Boolean) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goToPoseDetectionButton.setOnClickListener {
            navTo(actionHomeFragmentToPoseDetectionFragment())
        }
    }
}
