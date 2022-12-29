/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appnumber.R
import dev.sertan.android.ram.appnumber.databinding.FragmentHomeBinding
import dev.sertan.android.ram.appnumber.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToAboutFragment
import dev.sertan.android.ram.appnumber.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToCountingFragment
import dev.sertan.android.ram.appnumber.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToDrawingFragment
import dev.sertan.android.ram.appnumber.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToPracticeFragment
import dev.sertan.android.ram.appnumber.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToTrainingFragment
import dev.sertan.android.ram.core.ui.util.labelWithoutPrefix
import dev.sertan.android.ram.core.ui.util.navTo
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.viewBinding
import kotlinx.coroutines.flow.FlowCollector

@AndroidEntryPoint
internal class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()

    private val voiceSupportStateFlowCollector = FlowCollector<Boolean> { isActive ->
        binding.changeVoiceSupportButton.isActivated = isActive
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
        repeatOnLifecycleStarted {
            viewModel.voiceSupportState.collect(voiceSupportStateFlowCollector)
        }
    }

    private fun setUpComponents(): Unit = with(binding) {
        titleTextView.text = requireContext().labelWithoutPrefix
        changeVoiceSupportButton.setOnClickListener { viewModel.changeVoiceSupportState() }
        aboutButton.setOnClickListener { navTo(actionHomeFragmentToAboutFragment()) }
        drawingButton.setOnClickListener { navTo(actionHomeFragmentToDrawingFragment()) }
        trainingButton.setOnClickListener { navTo(actionHomeFragmentToTrainingFragment()) }
        practiceButton.setOnClickListener { navTo(actionHomeFragmentToPracticeFragment()) }
        countingButton.setOnClickListener { navTo(actionHomeFragmentToCountingFragment()) }
    }
}
