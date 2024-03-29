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

internal class LookLeftMotion : Motion {

    override val descriptionResId: Int = R.string.look_left

    override fun check(pose: Pose): Boolean {
        val leftEar = pose.getPoseLandmark(PoseLandmark.LEFT_EAR)
        val leftEyeOuter = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER)
        if (leftEar == null || leftEyeOuter == null) return false
        return leftEar.position.y > leftEyeOuter.position.y
    }
}
