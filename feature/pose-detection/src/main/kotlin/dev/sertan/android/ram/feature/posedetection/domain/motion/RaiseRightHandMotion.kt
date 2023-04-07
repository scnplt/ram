/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.posedetection.domain.motion

import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import dev.sertan.android.ram.feature.posedetection.R

internal class RaiseRightHandMotion : Motion {

    override val descriptionResId: Int = R.string.raise_right_hand

    override fun check(pose: Pose): Boolean {
        val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
        val rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)
        if (rightWrist == null || rightMouth == null) return false
        return rightWrist.position.x < rightMouth.position.x
    }
}
