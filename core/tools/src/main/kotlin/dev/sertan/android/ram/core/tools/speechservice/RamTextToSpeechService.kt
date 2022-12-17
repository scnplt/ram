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
import android.speech.tts.TextToSpeech.LANG_MISSING_DATA
import android.speech.tts.TextToSpeech.QUEUE_FLUSH
import android.speech.tts.TextToSpeech.SUCCESS
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryWithLogger
import java.util.Locale
import javax.inject.Inject

private const val ENGINE_PACKAGE = "com.google.android.tts"
private const val ENGINE_MARKET_ID = "https://play.google.com/store/apps/details?id=$ENGINE_PACKAGE"

internal class RamTextToSpeechService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val logger: RamLogger,
    private val locale: Locale
) : TextToSpeechService {

    private lateinit var textToSpeech: TextToSpeech

    private val listener = TextToSpeech.OnInitListener { status ->
        check(status == SUCCESS)
        val result = textToSpeech.setLanguage(locale)
        if (result == LANG_MISSING_DATA) startActivity(ACTION_INSTALL_TTS_DATA)
    }

    init {
        setUpIfNeeded()
    }

    private fun setUpIfNeeded() {
        if (this::textToSpeech.isInitialized) return
        if (isGoogleSpeechServiceExist()) {
            textToSpeech = TextToSpeech(context, listener)
            return
        }
        startActivity(ACTION_VIEW, ENGINE_MARKET_ID.toUri())
    }

    private fun isGoogleSpeechServiceExist(): Boolean = tryWithLogger(logger) {
        with(context.packageManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getPackageInfo(ENGINE_PACKAGE, PackageInfoFlags.of(0))
            } else {
                getPackageInfo(ENGINE_PACKAGE, 0)
            }
        }
    }

    private fun startActivity(action: String, uri: Uri? = null) {
        val intent = Intent(action).setData(uri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun speak(message: String?) {
        if (message == null) return
        setUpIfNeeded()
        val formattedMessage = message.replace("\\s+".toRegex(), ", ")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(formattedMessage, QUEUE_FLUSH, null, "")
            return
        }
        textToSpeech.speak(formattedMessage, QUEUE_FLUSH, null)
    }

    override fun stop() {
        textToSpeech.stop()
    }

    override fun shutdown(): Unit = textToSpeech.shutdown()
}
