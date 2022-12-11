/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.domain.usecase

import android.content.Context
import android.speech.tts.TextToSpeech
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sertan.android.ram.core.common.Dispatcher
import dev.sertan.android.ram.core.common.RamDispatcher
import dev.sertan.android.ram.core.data.repository.UserSettingsRepository
import dev.sertan.android.ram.core.domain.util.speak
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val VOICE_SUPPORT_DEFAULT_STATE = true
private const val VOICE_SUPPORT_LANG = "tr_TR"

@Singleton
class VoiceSupportUseCase @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository,
    @Dispatcher(RamDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext context: Context
) {

    private lateinit var textToSpeech: TextToSpeech

    private val textToSpeechInitListener = TextToSpeech.OnInitListener { status ->
        if (status == TextToSpeech.SUCCESS) textToSpeech.language = Locale(VOICE_SUPPORT_LANG)
    }

    init {
        textToSpeech = TextToSpeech(context, textToSpeechInitListener)
        initializeVoiceSupportStateIfNeeded()
    }

    private fun initializeVoiceSupportStateIfNeeded() {
        CoroutineScope(ioDispatcher).launch {
            if (getCurrentState() != null) return@launch
            userSettingsRepository.setVoiceSupportState(VOICE_SUPPORT_DEFAULT_STATE)
        }
    }

    private suspend fun getCurrentState(): Boolean? =
        userSettingsRepository.getVoiceSupportState().getOrNull()

    fun getVoiceSupportStateStream(): Flow<Boolean> =
        userSettingsRepository.getVoiceSupportStateStream().map { it.getOrNull() == true }

    fun speak(message: String?): Unit = textToSpeech.speak(message)

    suspend fun checkStateAndSpeak(message: String?) {
        if (getCurrentState() == true) textToSpeech.speak(message)
    }

    fun stopSpeech() {
        textToSpeech.stop()
    }

    suspend fun change() {
        val currentState = getCurrentState() ?: VOICE_SUPPORT_DEFAULT_STATE
        userSettingsRepository.setVoiceSupportState(isActive = !currentState)
    }
}