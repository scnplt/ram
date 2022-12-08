/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.util.extension

import android.content.Context
import android.content.res.Resources
import android.media.MediaPlayer
import androidx.annotation.ArrayRes
import androidx.annotation.RawRes

private const val NO_COLOR = Int.MIN_VALUE

fun Context.playSound(@RawRes soundResId: Int): Unit = MediaPlayer.create(this, soundResId).start()

fun Resources.getColorList(@ArrayRes colorListId: Int): List<Int> {
    val colorList: List<Int>
    obtainTypedArray(colorListId).also {
        colorList = (0 until it.length()).mapNotNull { colorIndex ->
            it.getColor(colorIndex, NO_COLOR).takeIf { color -> color != NO_COLOR }
        }
    }.recycle()
    return colorList
}
