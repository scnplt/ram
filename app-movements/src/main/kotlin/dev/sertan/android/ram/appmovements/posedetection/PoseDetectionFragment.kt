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
import androidx.fragment.app.Fragment
import dev.sertan.android.ram.appmovements.R
import dev.sertan.android.ram.appmovements.databinding.FragmentPoseDetectionBinding
import dev.sertan.android.ram.core.ui.util.viewBinding

internal class PoseDetectionFragment : Fragment(R.layout.fragment_pose_detection) {

    private var detector: PoseDetector? = null

    private val binding by viewBinding(FragmentPoseDetectionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detector = PoseDetector(
            display = requireActivity().windowManager.defaultDisplay,
            overlayView = binding.overlayView
        )
        detector?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detector?.stop()
        detector = null
    }
}
