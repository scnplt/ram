/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.tools.speechservice

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.pm.PackageManager.PackageInfoFlags
import android.net.Uri
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
import android.speech.tts.TextToSpeech.QUEUE_FLUSH
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryWithLogger
import java.util.Locale
import javax.inject.Inject

private const val DEFAULT_LANGUAGE_TAG = "tr_TR"
private const val SPEECH_SERVICE_PACKAGE = "com.google.android.tts"
private const val SPEECH_SERVICE_MARKET_ID =
    "https://play.google.com/store/apps/details?id=$SPEECH_SERVICE_PACKAGE"

internal class DefaultSpeechService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: RamLogger
) : RamSpeechService {

    private lateinit var tts: TextToSpeech
    private val locale = Locale(DEFAULT_LANGUAGE_TAG)

    init {
        setUpSpeechService()
    }

    private fun setUpSpeechService() {
        if (this::tts.isInitialized) return
        if (!isGoogleSpeechServiceExist()) {
            startIntent(ACTION_VIEW, SPEECH_SERVICE_MARKET_ID.toUri())
            return
        }
        tts = TextToSpeech(context) { status ->
            if (status != TextToSpeech.SUCCESS) throw IllegalStateException()
            val result = tts.setLanguage(locale)
            if (result == TextToSpeech.LANG_MISSING_DATA) startIntent(ACTION_INSTALL_TTS_DATA)
        }
    }

    private fun isGoogleSpeechServiceExist(): Boolean = tryWithLogger(logger) {
        with(context.packageManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getPackageInfo(SPEECH_SERVICE_PACKAGE, PackageInfoFlags.of(0))
            } else {
                getPackageInfo(SPEECH_SERVICE_PACKAGE, 0)
            }
        }
    }

    private fun startIntent(action: String, uri: Uri? = null) {
        val intent = Intent(action).apply {
            uri?.let(::setData)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    override fun speak(message: String?) {
        setUpSpeechService()
        val ttsMessage = message?.replace("\\s+".toRegex(), ", ") ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(ttsMessage, QUEUE_FLUSH, null, "")
            return
        }
        tts.speak(ttsMessage, QUEUE_FLUSH, null)
    }

    override fun stop() {
        tts.stop()
    }

    override fun shutdown(): Unit = tts.shutdown()
}
