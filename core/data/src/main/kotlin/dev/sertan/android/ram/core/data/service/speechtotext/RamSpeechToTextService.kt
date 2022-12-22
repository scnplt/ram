/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.service.speechtotext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH
import android.speech.RecognizerIntent.EXTRA_LANGUAGE
import android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL
import android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.RESULTS_RECOGNITION
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sertan.android.ram.core.common.di.DEFAULT_LOCALE_LANG_TAG
import javax.inject.Inject

internal class RamSpeechToTextService @Inject constructor(
    @ApplicationContext private val context: Context
) : SpeechToTextService {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognizerIntent: Intent

    init {
        setUpIfNeeded()
    }

    private fun setUpIfNeeded() {
        if (this::speechRecognizer.isInitialized && this::recognizerIntent.isInitialized) return
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        recognizerIntent = Intent(ACTION_RECOGNIZE_SPEECH)
            .putExtra(EXTRA_LANGUAGE, DEFAULT_LOCALE_LANG_TAG)
            .putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_FREE_FORM)
    }

    override fun start() {
        setUpIfNeeded()
        speechRecognizer.startListening(recognizerIntent)
    }

    override fun stop(): Unit = speechRecognizer.stopListening()

    override fun shutdown(): Unit = speechRecognizer.destroy()

    override fun setListener(listener: SpeechToTextService.Listener) {
        val recognizerListener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) = Unit
            override fun onBeginningOfSpeech() = Unit
            override fun onRmsChanged(rmsdB: Float) = Unit
            override fun onBufferReceived(buffer: ByteArray?) = Unit
            override fun onEndOfSpeech() = Unit
            override fun onPartialResults(partialResults: Bundle?) = Unit
            override fun onEvent(eventType: Int, params: Bundle?) = Unit

            override fun onError(error: Int): Unit = listener.onError(error)

            override fun onResults(results: Bundle?) {
                results?.getStringArrayList(RESULTS_RECOGNITION)?.let(listener::onResult)
            }
        }
        speechRecognizer.setRecognitionListener(recognizerListener)
    }
}
