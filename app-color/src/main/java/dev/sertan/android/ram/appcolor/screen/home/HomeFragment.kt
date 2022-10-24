/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcolor.R
import dev.sertan.android.ram.appcolor.databinding.FragmentHomeBinding
import dev.sertan.android.ram.appcolor.screen.home.HomeFragmentDirections.Companion.actionHomeFragmentToDetailBottomSheet
import dev.sertan.android.ram.coreui.util.navigateTo
import dev.sertan.android.ram.coreui.util.showToast
import dev.sertan.android.ram.coreui.util.startAnimation
import dev.sertan.android.ram.coreui.util.viewBinding
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()

    private val uiStateCollector = FlowCollector<HomeUiState> { uiState ->
        context?.showToast(uiState.message.data)
        binding.volumeButton.isActivated = uiState.isVolumeActive
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(uiStateCollector)
            }
        }
    }

    private fun setUpComponents(): Unit = with(binding) {
        helpButton.setOnClickListener {
            startActivity(Intent(activity, OssLicensesMenuActivity::class.java))
        }
        volumeButton.setOnClickListener { viewModel.changeVoiceSupportState() }
        startButton.startAnimation(R.anim.anim_shake)
        startButton.setOnClickListener {
            navigateTo(
                actionHomeFragmentToDetailBottomSheet(
                    isQuestionButtonActive = viewModel.isTrainingCompletedBefore
                )
            )
        }
    }
}
