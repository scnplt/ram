/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.posedetection.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.core.ui.util.playCorrectSound
import dev.sertan.android.ram.core.ui.util.popBackStack
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.viewBinding
import dev.sertan.android.ram.feature.posedetection.R
import dev.sertan.android.ram.feature.posedetection.databinding.FragmentPoseDetectionBinding
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class PoseDetectionFragment : Fragment(R.layout.fragment_pose_detection) {

    private val viewModel by viewModels<PoseDetectionViewModel>()
    private val binding by viewBinding(FragmentPoseDetectionBinding::bind)

    private var detector: PoseDetector? = null

    private val poseListener = PoseDetector.Listener { pose ->
        viewModel.checkMotion(pose = pose) { isCorrect ->
            if (!isCorrect) return@checkMotion
            playCorrectSound()
            viewModel.isValidationActive = false
            binding.answerStateImageView.isInvisible = false
            viewModel.resetCount()
        }
    }

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect { uiState ->
            with(binding) {
                nextButton.isInvisible = !uiState.isNextButtonVisible
                finishButton.isInvisible = !uiState.isFinishButtonVisible
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detector = PoseDetector(
            display = requireActivity().windowManager.defaultDisplay,
            overlayView = binding.overlayView
        ).apply { listener = poseListener }
        detector?.start()

        repeatOnLifecycleStarted(onLifecycleStarted)

        with(binding) {
            exitButton.setOnClickListener { popBackStack() }
            nextButton.setOnClickListener {
                binding.answerStateImageView.isInvisible = true
                viewModel.goToNextMotion()
            }
            finishButton.setOnClickListener { popBackStack() }
        }
    }

    override fun onStop() {
        viewModel.stopSpeech()
        super.onStop()
    }

    override fun onDestroy() {
        detector?.releaseCamera()
        detector = null
        super.onDestroy()
    }
}
