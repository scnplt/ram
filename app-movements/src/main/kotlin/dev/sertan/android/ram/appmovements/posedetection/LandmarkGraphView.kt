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
import android.util.AttributeSet
import android.view.View
import com.google.mlkit.vision.pose.PoseLandmark

internal class LandmarkGraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val points = mutableListOf<Pair<Float, Float>>()
    private val lines = mutableListOf<Pair<Pair<Float, Float>, Pair<Float, Float>>>()

    private val pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 28f
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 10f
    }

    private fun addPoint(landmark: PoseLandmark?) {
        if (landmark == null) return
        points.add(landmark.position.x to landmark.position.y)
    }

    fun addLine(start: PoseLandmark?, stop: PoseLandmark?) {
        start?.let { addPoint(it) }
        stop?.let { addPoint(it) }
        if (start == null || stop == null) return invalidate()

        lines.add(Pair(start.position.x to start.position.y, stop.position.x to stop.position.y))
        invalidate()
    }

    fun clear() {
        lines.clear()
        points.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        lines.forEach { (start, stop) ->
            canvas.drawLine(start.second, start.first, stop.second, stop.first, linePaint)
        }
        points.forEach { (x, y) -> canvas.drawPoint(y, x, pointPaint) }
    }
}
