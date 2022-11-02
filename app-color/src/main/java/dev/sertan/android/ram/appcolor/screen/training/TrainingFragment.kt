/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.training

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcolor.R
import dev.sertan.android.ram.appcolor.databinding.FragmentTrainingBinding
import dev.sertan.android.ram.coreui.util.viewBinding
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class TrainingFragment : Fragment(R.layout.fragment_training) {
    private val binding by viewBinding(FragmentTrainingBinding::bind)
    private val viewModel by viewModels<TrainingViewModel>()

    private val currentMaterialFlowCollector = FlowCollector<TrainingUiState> {
        with(binding) {
            it.material?.let { material ->
                descriptionTextView.text = material.description
                mediaImageView.contentDescription = material.description
                // mediaImageView.loadFromUrl(material.mediaUrl)
                // TODO: Uncomment previous line
            }
            backButton.isVisible = it.isBackButtonVisible
            forwardButton.isVisible = it.isForwardButtonVisible
            finishButton.isVisible = it.isFinishButtonVisible
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(currentMaterialFlowCollector)
            }
        }
    }

    private fun setUpComponents(): Unit = with(binding) {
        forwardButton.setOnClickListener { viewModel.goToNextMaterial() }
        backButton.setOnClickListener { viewModel.goToPreviousMaterial() }
        materialCardView.setOnClickListener {
            // TODO: material description speech
        }
    }
}
