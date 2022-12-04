/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.util.extension

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import androidx.annotation.RawRes

fun Context.showToast(message: String?) {
    message?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
}

fun Context.playSound(@RawRes soundResId: Int): Unit = MediaPlayer.create(this, soundResId).start()
