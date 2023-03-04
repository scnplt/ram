/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.ui.home

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appselection.R
import dev.sertan.android.ram.appselection.databinding.FragmentHomeBinding
import dev.sertan.android.ram.appselection.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToDrawingFragment
import dev.sertan.android.ram.appselection.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToPracticeGraph
import dev.sertan.android.ram.appselection.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToTrainingFragment
import dev.sertan.android.ram.core.ui.fragment.texttospeechprovider.TextToSpeechProviderFragment
import dev.sertan.android.ram.core.ui.util.labelWithoutPrefix
import dev.sertan.android.ram.core.ui.util.navTo
import dev.sertan.android.ram.core.ui.util.navigateToOssLicenses
import dev.sertan.android.ram.core.ui.util.viewBinding

@AndroidEntryPoint
internal class HomeFragment : TextToSpeechProviderFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onTextToSpeechStateChanged(isActive: Boolean) {
        binding.changeVoiceSupportButton.isActivated = isActive
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            titleTextView.text = requireContext().labelWithoutPrefix
            changeVoiceSupportButton.setOnClickListener { changeTextToSpeechState() }
            aboutButton.setOnClickListener { navigateToOssLicenses() }
            drawingButton.setOnClickListener { navTo(actionHomeFragmentToDrawingFragment()) }
            trainingButton.setOnClickListener { navTo(actionHomeFragmentToTrainingFragment()) }
            practiceButton.setOnClickListener { navTo(actionHomeFragmentToPracticeGraph()) }
        }
    }
}
