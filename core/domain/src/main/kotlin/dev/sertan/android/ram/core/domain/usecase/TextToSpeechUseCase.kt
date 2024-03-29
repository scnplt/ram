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
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sertan.android.ram.core.data.repository.UserSettingsRepository
import dev.sertan.android.ram.core.data.service.texttospeech.TextToSpeechService
import dev.sertan.android.ram.core.domain.Dispatcher
import dev.sertan.android.ram.core.domain.RamDispatcher.IO
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val VOICE_SUPPORT_DEFAULT_STATE = true

@Singleton
class TextToSpeechUseCase @Inject constructor(
    @ApplicationContext context: Context,
    private val userSettingsRepository: UserSettingsRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val textToSpeechService: TextToSpeechService
) {

    private val resources = context.resources

    init {
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

    fun speak(message: String?) = textToSpeechService.speak(message)

    fun speak(@StringRes messageResId: Int?) = messageResId?.let { speak(resources.getString(it)) }

    suspend fun checkStateAndSpeak(message: String?) {
        if (getCurrentState() == true) speak(message)
    }

    suspend fun checkStateAndSpeak(@StringRes messageResId: Int?) {
        if (getCurrentState() == true) speak(messageResId)
    }

    fun stopSpeech(): Unit = textToSpeechService.stop()

    suspend fun change() {
        val currentState = getCurrentState() ?: VOICE_SUPPORT_DEFAULT_STATE
        userSettingsRepository.setVoiceSupportState(isActive = !currentState)
    }
}
