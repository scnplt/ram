/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.posedetection

import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@HiltViewModel
internal class PoseDetectionViewModel @Inject constructor() : ViewModel() {

    private val processScope = CoroutineScope(Dispatchers.IO)

    fun processPoseWithPairs(
        pose: Pose,
        block: (List<Pair<PoseLandmark?, PoseLandmark?>>) -> Unit
    ) {
        processScope.launch {
            val poses = getLandmarkPairs(pose)
            block(poses)
        }
    }

    private fun getLandmarkPairs(pose: Pose): List<Pair<PoseLandmark?, PoseLandmark?>> {
        val nose = pose.getPoseLandmark(PoseLandmark.NOSE)
        val leftEyeInner = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER)
        val leftEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE)
        val leftEyeOuter = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER)
        val rightEyeInner = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER)
        val rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE)
        val rightEyeOuter = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER)
        val leftEar = pose.getPoseLandmark(PoseLandmark.LEFT_EAR)
        val rightEar = pose.getPoseLandmark(PoseLandmark.RIGHT_EAR)
        val leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
        val rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)
        val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
        val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
        val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
        val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
        val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
        val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
        val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
        val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
        val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
        val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
        val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
        val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
        val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)
        val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
        val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
        val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
        val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)

        return listOf(
            nose to leftEyeInner,
            leftEyeInner to leftEyeOuter,
            leftEye to leftEyeInner,
            leftEyeOuter to leftEar,
            nose to rightEyeInner,
            rightEyeInner to rightEyeOuter,
            rightEye to rightEyeInner,
            rightEyeOuter to rightEar,
            leftMouth to rightMouth,
            leftShoulder to rightShoulder,
            leftShoulder to leftElbow,
            leftElbow to leftWrist,
            leftWrist to leftPinky,
            leftWrist to leftIndex,
            leftWrist to leftThumb,
            leftIndex to leftPinky,
            rightShoulder to rightElbow,
            rightElbow to rightWrist,
            rightWrist to rightPinky,
            rightWrist to rightIndex,
            rightWrist to rightThumb,
            rightIndex to rightPinky,
            leftShoulder to leftHip,
            leftHip to leftKnee,
            leftKnee to leftAnkle,
            leftAnkle to leftHeel,
            leftHeel to leftFootIndex,
            leftAnkle to leftFootIndex,
            rightShoulder to rightHip,
            rightHip to rightKnee,
            rightKnee to rightAnkle,
            rightAnkle to rightHeel,
            rightHeel to rightFootIndex,
            rightAnkle to rightFootIndex,
        )
    }

    override fun onCleared() {
        processScope.cancel()
        super.onCleared()
    }
}
