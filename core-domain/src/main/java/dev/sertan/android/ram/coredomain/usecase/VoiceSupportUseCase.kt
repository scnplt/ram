/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredomain.usecase

import android.speech.tts.TextToSpeech
import dev.sertan.android.ram.corecommon.repository.UserSettingsRepository
import dev.sertan.android.ram.coredomain.util.speak
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

private const val VOICE_SUPPORT_DEFAULT_STATE = true

@Singleton
class VoiceSupportUseCase @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository,
    private val textToSpeech: TextToSpeech,
) {

    suspend fun checkVoiceSupportStateAndSpeak(message: String?) {
        if (getVoiceSupportState()) textToSpeech.speak(message)
    }

    fun speak(message: String?): Unit = textToSpeech.speak(message)

    fun stopSpeech() {
        textToSpeech.stop()
    }

    fun getVoiceSupportStateAsStream(coroutineScope: CoroutineScope): StateFlow<Boolean> =
        with(userSettingsRepository) {
            getVoiceSupportStateAsStream().map { result -> result.getOrNull() == true }
                .onStart {
                    getVoiceSupportState().getOrNull()
                        ?: setVoiceSupportState(VOICE_SUPPORT_DEFAULT_STATE)
                }
                .stateIn(
                    scope = coroutineScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = VOICE_SUPPORT_DEFAULT_STATE
                )
        }

    private suspend fun getVoiceSupportState(): Boolean =
        userSettingsRepository.getVoiceSupportState().getOrNull() == true

    suspend fun change() {
        val currentState = userSettingsRepository.getVoiceSupportState().getOrNull() ?: return
        userSettingsRepository.setVoiceSupportState(isActive = !currentState)
    }
}
