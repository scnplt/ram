/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

@file:Suppress("TooManyFunctions")

package dev.sertan.android.ram.core.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.content.res.Resources
import android.media.MediaPlayer
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.annotation.ColorInt
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.button.MaterialButton
import kotlin.properties.ReadOnlyProperty
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber

private const val NO_COLOR = Int.MIN_VALUE

fun SharedPreferences.getStringAsStream(dataKey: String): Flow<String?> = callbackFlow {
    trySend(getString(dataKey, null))
    val sharedPrefListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPref, key ->
        if (dataKey == key) trySend(sharedPref.getString(dataKey, null))
    }
    registerOnSharedPreferenceChangeListener(sharedPrefListener)
    awaitClose { unregisterOnSharedPreferenceChangeListener(sharedPrefListener) }
}

@Suppress("TooGenericExceptionCaught")
suspend fun <R> tryGetResultWithLog(block: suspend () -> R): Result<R> = try {
    Result.success(block())
} catch (exception: Throwable) {
    Timber.e(exception)
    Result.failure(exception)
}

fun <I, O> Flow<I>.tryGetResultWithLog(block: (I) -> O): Flow<Result<O>> =
    map { Result.success(block(it)) }.catch { exception ->
        Timber.e(exception)
        Result.failure<O>(exception)
    }

@Suppress("MagicNumber")
fun percent(value: Float, total: Float): Float = if (total == 0f) 0f else value / total * 100

@Suppress("DEPRECATION")
fun TextToSpeech.speak(message: String?) {
    speak(message?.replace("\\s+".toRegex(), ", ") ?: return, TextToSpeech.QUEUE_FLUSH, null)
}

fun Fragment.navigateTo(navDirections: NavDirections): Unit =
    findNavController().navigate(navDirections)

fun Fragment.popBackStack() {
    findNavController().popBackStack()
}

fun <VB : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> VB
): ReadOnlyProperty<Fragment, VB> = FragmentViewBindingDelegate(viewBindingFactory)

fun Fragment.showToast(message: String?): Unit = requireContext().showToast(message)

fun Fragment.navigateToOssLicensesActivity(): Unit =
    startActivity(Intent(requireActivity(), OssLicensesMenuActivity::class.java))

fun <VB : ViewBinding> AppCompatActivity.viewBinding(inflate: (LayoutInflater) -> VB) =
    lazy { inflate(layoutInflater) }

fun Context.showToast(message: String?) {
    message?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
}

fun Context.playSound(@RawRes soundResId: Int): Unit = MediaPlayer.create(this, soundResId).start()

fun Resources.getColorList(@ArrayRes colorListId: Int): List<Int> = obtainTypedArray(colorListId)
    .use {
        (0 until it.length()).mapNotNull { colorIndex ->
            it.getColor(colorIndex, NO_COLOR).takeIf { color -> color != NO_COLOR }
        }
    }

fun MaterialButton.setIconTint(@ColorInt color: Int) {
    iconTint = ColorStateList.valueOf(color)
}

fun ImageView.loadFromUrl(uri: Uri?) {
    GlideApp.with(this).load(uri).into(this)
}

@Suppress("MagicNumber")
fun RatingBar.setRatingByPercent(percent: Float) {
    rating = percent * numStars / 100
}
