/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.posedetection

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.View
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark

internal class LandmarkView(context: Context) : View(context) {

    private var pose: Pose? = null

    var pointPaint: Paint? = null

    var xRatio = 1f
    var yRatio = 1f

    fun updatePose(newPose: Pose?) {
        pose = newPose ?: return
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) return
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        drawPose(canvas = canvas, pose = pose, paint = pointPaint)
    }

    private fun drawPose(canvas: Canvas, pose: Pose?, paint: Paint?) {
        if (pose == null || paint == null) return
        pose.allPoseLandmarks.forEach { landmark ->
            getPositions(landmark).let { (x, y) -> canvas.drawPoint(x, y, paint) }
        }
    }

    private fun getPositions(landmark: PoseLandmark): Pair<Float, Float> =
        with(landmark) { position.y * xRatio to position.x * yRatio }
}
