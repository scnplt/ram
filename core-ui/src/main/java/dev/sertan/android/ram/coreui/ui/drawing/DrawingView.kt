/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.ui.drawing

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.AttrRes
import dev.sertan.android.ram.coreui.R
import kotlin.properties.Delegates

private const val DEFAULT_BG_COLOR = Color.WHITE
private const val DEFAULT_PEN_COLOR = Color.BLACK
private const val DEFAULT_ERASER_WIDTH = 80f
private const val DEFAULT_PEN_WIDTH = 8f

class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private data class Line(
        val path: Path,
        var color: Int,
        var width: Float
    )

    enum class Mode { DRAW, ERASE }

    var listener: Listener? = null
    private var previousLines = mutableListOf<Line>()
    private var canvasColor = DEFAULT_BG_COLOR
    private var penWidth = DEFAULT_PEN_WIDTH
    private var eraserWidth = DEFAULT_ERASER_WIDTH

    private var currentLine: Line

    var currentMode by Delegates.observable(Mode.DRAW) { _, oldMode, newMode ->
        if (oldMode == newMode) return@observable
        listener?.onModeChanged(newMode)
    }

    var penColor: Int by Delegates.observable(DEFAULT_PEN_COLOR) { _, _, newColorRes ->
        listener?.onPenColorChanged(color = newColorRes)
    }

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.DrawingView).use {
            canvasColor = it.getColor(R.styleable.DrawingView_canvasColor, canvasColor)
            penColor = it.getColor(R.styleable.DrawingView_penColor, penColor)
            penWidth = it.getDimension(R.styleable.DrawingView_penWidth, penWidth)
            eraserWidth = it.getDimension(R.styleable.DrawingView_eraserWidth, eraserWidth)
        }
        setBackgroundColor(canvasColor)
        currentLine = Line(path = Path(), color = penColor, width = penWidth)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean = when (event?.action) {
        MotionEvent.ACTION_DOWN -> {
            updateCurrentLineByMode(mode = currentMode)
            currentLine.path.moveTo(event.x, event.y)
            true
        }
        MotionEvent.ACTION_MOVE -> {
            currentLine.path.lineTo(event.x, event.y)
            invalidate()
            true
        }
        MotionEvent.ACTION_UP -> {
            previousLines.add(currentLine)
            currentLine = currentLine.copy(path = Path())
            true
        }
        else -> super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        previousLines.forEach { line -> drawLine(canvas = canvas, line = line) }
        drawLine(canvas = canvas, line = currentLine)
        super.onDraw(canvas)
    }

    private fun drawLine(canvas: Canvas?, line: Line) {
        paint.color = line.color
        paint.strokeWidth = line.width
        canvas?.drawPath(line.path, paint)
    }

    private fun updateCurrentLineByMode(mode: Mode) = when (mode) {
        Mode.DRAW -> {
            currentLine.color = penColor
            currentLine.width = penWidth
        }
        Mode.ERASE -> {
            currentLine.color = canvasColor
            currentLine.width = eraserWidth
        }
    }

    fun clearCanvas() {
        currentLine = currentLine.copy(path = Path())
        previousLines.clear()
        invalidate()
    }

    interface Listener {
        fun onModeChanged(mode: Mode) {}
        fun onPenColorChanged(color: Int) {}
    }
}
