/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.util

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ArrayRes
import androidx.viewbinding.ViewBinding

private const val NO_COLOR = Int.MIN_VALUE
private const val APP_NAME_PATTERN = "(?<=RAM-\\d\\s|\\d\\d\\s).*"
private const val APP_NO_PATTERN = "(?<=RAM-)\\d+"

val Context.labelWithoutPrefix: String?
    get() = APP_NAME_PATTERN.toRegex().find(applicationInfo.loadLabel(packageManager))?.value

val Context.appNo: Int?
    get() = APP_NO_PATTERN.toRegex()
        .find(applicationInfo.loadLabel(packageManager))?.value?.toIntOrNull()

fun Resources.getColorList(@ArrayRes colorListId: Int): List<Int> {
    val colorList: List<Int>
    obtainTypedArray(colorListId).also {
        colorList = (0 until it.length()).mapNotNull { colorIndex ->
            it.getColor(colorIndex, NO_COLOR).takeIf { color -> color != NO_COLOR }
        }
    }.recycle()
    return colorList
}

fun <VB : ViewBinding> ViewGroup.viewBinding(inflater: (LayoutInflater, ViewGroup) -> VB): VB =
    inflater(LayoutInflater.from(context), this)
