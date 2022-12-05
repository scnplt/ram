/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.ui.screen.training

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcolor.R
import dev.sertan.android.ram.appcolor.databinding.FragmentTrainingBinding
import dev.sertan.android.ram.appcolor.ui.screen.training.TrainingFragmentDirections.Companion.actionTrainingFragmentToPracticeFragment
import dev.sertan.android.ram.core.util.extension.loadFromUrl
import dev.sertan.android.ram.core.util.extension.navigateTo
import dev.sertan.android.ram.core.util.extension.popBackStack
import dev.sertan.android.ram.core.util.extension.viewBinding
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
                attributionTextView.text = getString(
                    dev.sertan.android.ram.core.R.string.this_icon_was_created_by,
                    material.attribution
                )
                mediaImageView.contentDescription = material.description
                mediaImageView.loadFromUrl(material.mediaUri)
            }
            backButton.isInvisible = !it.isBackButtonVisible
            forwardButton.isInvisible = !it.isForwardButtonVisible
            finishButton.isInvisible = !it.isFinishButtonVisible
            progressIndicator.progress = it.progress
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
        with(viewModel) {
            forwardButton.setOnClickListener { goToNextMaterial() }
            backButton.setOnClickListener { goToPreviousMaterial() }
            exitButton.setOnClickListener { popBackStack() }
            materialCardView.setOnClickListener { speakCurrentMaterialDescription() }
            finishButton.setOnClickListener {
                navigateTo(actionTrainingFragmentToPracticeFragment())
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSpeech()
    }
}
