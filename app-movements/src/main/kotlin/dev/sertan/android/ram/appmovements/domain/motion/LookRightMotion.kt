/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.domain.motion

import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import dev.sertan.android.ram.appmovements.R

internal class LookRightMotion : Motion {

    override val descriptionResId: Int = R.string.look_right

    override fun check(pose: Pose): Boolean {
        val rightEar = pose.getPoseLandmark(PoseLandmark.RIGHT_EAR)
        val rightEyeOuter = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER)
        if (rightEar == null || rightEyeOuter == null) return false
        return rightEar.position.y < rightEyeOuter.position.y
    }
}
