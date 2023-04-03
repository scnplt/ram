/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.posedetection

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appmovements.R
import dev.sertan.android.ram.appmovements.databinding.FragmentPoseDetectionBinding
import dev.sertan.android.ram.core.ui.util.playCorrectSound
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@AndroidEntryPoint
internal class PoseDetectionFragment : Fragment(R.layout.fragment_pose_detection) {

    private val viewModel by viewModels<PoseDetectionViewModel>()
    private val binding by viewBinding(FragmentPoseDetectionBinding::bind)

    private var detector: PoseDetector? = null

    private val poseListener = PoseDetector.Listener {
        viewModel.checkMotion(pose = it) { isCorrect ->
            if (isCorrect) {
                context?.playCorrectSound()
                viewModel.resetCount()
                delay(1000L)
                viewModel.goToNextMotion()
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detector?.stop()
        detector = null
    }
}
