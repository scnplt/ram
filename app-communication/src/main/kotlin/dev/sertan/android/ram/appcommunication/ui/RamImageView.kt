/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import dev.sertan.android.ram.appcommunication.ui.bodyparts.BodyPart
import dev.sertan.android.ram.core.ui.RamActivity

internal class RamImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    private var currentPart: BodyPart? = null

    private fun getRectFFromBodyPart(part: BodyPart): RectF = RectF(
        part.left * width,
        part.top * height,
        part.right * width,
        part.bottom * height
    )

    fun setPart(part: BodyPart) {
        currentPart = part
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        try {
            if (event?.action == MotionEvent.ACTION_DOWN
                && event.x < currentPart!!.right * width
                && event.x > currentPart!!.left * width
                && event.y < currentPart!!.bottom * height
                && event.y > currentPart!!.top * height
            ) {
                Log.d("RAM_LOG_TAG", "onTouchEvent: correct")
            } else {
                Log.d("RAM_LOG_TAG", "onTouchEvent: wrong")
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }



        return super.onTouchEvent(event)
    }
}
