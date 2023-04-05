/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.posedetection.motion

import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import dev.sertan.android.ram.appmovements.R

internal class RaiseLeftHandMotion : Motion {

    override val descriptionResId: Int = R.string.raise_left_hand

    override fun check(pose: Pose): Boolean {
        val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
        if (leftWrist == null || leftMouth == null) return false
        return leftWrist.position.x < leftMouth.position.x
    }
}
