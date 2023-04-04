/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.posedetection.motion

import androidx.annotation.StringRes
import com.google.mlkit.vision.pose.Pose

internal interface Motion {

    @get:StringRes
    val descriptionResId: Int

    fun check(pose: Pose): Boolean
}
