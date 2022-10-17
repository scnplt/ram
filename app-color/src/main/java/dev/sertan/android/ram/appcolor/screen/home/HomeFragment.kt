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
import dev.sertan.android.ram.coreui.extension.navigateTo
import dev.sertan.android.ram.coreui.extension.provideBinding
import dev.sertan.android.ram.coreui.extension.showToast
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class HomeFragment : Fragment(R.layout.fragment_home) {
    private val homeViewModel by viewModels<HomeViewModel>()
    private val binding by provideBinding(FragmentHomeBinding::bind)

    private val homeUiStateCollector = FlowCollector<HomeUiState> { uiState ->
        uiState.errorMessage.data?.let { message -> showToast(message) }
        binding.volumeImageView.isActivated = uiState.isVolumeActive
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            startButton.setOnClickListener {
                navigateTo(HomeFragmentDirections.actionHomeFragmentToTrainFragment())
            }
            volumeImageView.setOnClickListener { homeViewModel.changeVolumeState() }
            helpImageView.setOnClickListener {
                // TODO delete this
                showToast("Help Button Clicked!")
            }
        }
    }

    private fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.homeUiState.collect(homeUiStateCollector)
            }
        }
    }
}
