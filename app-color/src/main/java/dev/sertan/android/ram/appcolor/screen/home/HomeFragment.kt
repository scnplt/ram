/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcolor.R
import dev.sertan.android.ram.appcolor.databinding.FragmentHomeBinding
import dev.sertan.android.ram.appcolor.screen.home.HomeFragmentDirections.Companion.actionHomeFragmentToDrawingFragment
import dev.sertan.android.ram.coreui.util.navigateTo
import dev.sertan.android.ram.coreui.util.showToast
import dev.sertan.android.ram.coreui.util.viewBinding
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
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.voiceSupportState.collect(voiceSupportStateFlowCollector)
            }
        }
    }

    private fun setUpComponents(): Unit = with(binding) {
        helpButton.setOnClickListener { showToast("Not yet implemented") }
        changeVoiceSupportButton.setOnClickListener { viewModel.changeVoiceSupportState() }
        trainingButton.setOnClickListener { showToast("Not yet implemented") }
        practiceButton.setOnClickListener { showToast("Not yet implemented") }
        drawingButton.setOnClickListener { navigateTo(actionHomeFragmentToDrawingFragment()) }
    }
}
