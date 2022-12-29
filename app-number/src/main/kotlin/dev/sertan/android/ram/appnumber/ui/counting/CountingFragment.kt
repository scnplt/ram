/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.ui.counting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appnumber.R
import dev.sertan.android.ram.appnumber.databinding.FragmentCountingBinding
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.viewBinding
import kotlinx.coroutines.flow.FlowCollector

@AndroidEntryPoint
internal class CountingFragment : Fragment(R.layout.fragment_counting) {
    private val binding by viewBinding(FragmentCountingBinding::bind)
    private val viewModel by viewModels<CountingViewModel>()

    private val uiStateFlowCollector = FlowCollector<CountingUiState> {
        // TODO: Not yet implemented
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
        repeatOnLifecycleStarted { viewModel.uiState.collect(uiStateFlowCollector) }
    }

    private fun setUpComponents(): Unit = with(binding) {
        micButton.setOnClickListener { viewModel.listenToNumber() }
    }
}
