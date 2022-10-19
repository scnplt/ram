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
import dev.sertan.android.ram.coreui.extension.provideBinding
import dev.sertan.android.ram.coreui.extension.showToast
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class HomeFragment : Fragment(R.layout.fragment_home) {
    private val homeViewModel by viewModels<HomeViewModel>()
    private val binding by provideBinding(FragmentHomeBinding::bind)

    private val homeUiStateCollector = FlowCollector<HomeUiState> {
        it.errorMessage.data?.let { message -> showToast(message) }
        binding.volumeButton.isActivated = it.isVolumeActive
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.homeUiState.collect(homeUiStateCollector)
            }
        }
        setUpListeners()
    }

    private fun setUpListeners(): Unit = with(binding) {
        volumeButton.setOnClickListener {
            homeViewModel.changeVolumeState()
            showToast("Volume Button Clicked!") // TODO delete this
        }
        helpButton.setOnClickListener {
            showToast("Help Button Clicked!") // TODO delete this
        }
        startButton.setOnClickListener {
            showToast("Start Button Clicked!") // TODO delete this
        }
    }
}
