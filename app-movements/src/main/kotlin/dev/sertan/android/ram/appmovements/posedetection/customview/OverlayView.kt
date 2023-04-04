/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.posedetection.customview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout
import com.google.mlkit.vision.pose.Pose
import dev.sertan.android.ram.appmovements.R

internal class OverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val landmarkView = LandmarkView(context)
    private val surfaceView = SurfaceView(context)

    private var pointColor: Int = DEFAULT_POINT_COLOR
    private var pointWidth = DEFAULT_POINT_WIDTH

    init {
        context.obtainStyledAttributes(attrs, R.styleable.OverlayView).apply {
            pointColor = getColor(R.styleable.OverlayView_pointColor, pointColor)
            pointWidth = getDimension(R.styleable.OverlayView_pointWidth, pointWidth)
        }.recycle()

        landmarkView.pointPaint = getPointPaint()

        addView(surfaceView)
        addView(landmarkView)
    }

    private fun getPointPaint(): Paint = Paint().apply {
        color = pointColor
        style = Paint.Style.FILL
        strokeCap = Paint.Cap.ROUND
        strokeWidth = pointWidth
    }

    internal fun updatePose(pose: Pose): Unit = landmarkView.updatePose(newPose = pose)

    internal fun setHolderCallback(callback: SurfaceHolder.Callback) {
        surfaceView.holder.addCallback(callback)
    }

    internal fun updateRatio(xRatio: Float, yRatio: Float) {
        landmarkView.xRatio = xRatio
        landmarkView.yRatio = yRatio
    }

    companion object {
        const val DEFAULT_POINT_COLOR = Color.RED
        const val DEFAULT_POINT_WIDTH = 16f
    }
}
