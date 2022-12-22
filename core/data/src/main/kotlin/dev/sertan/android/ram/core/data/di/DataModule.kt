/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.core.data.repository.RamUserSettingsRepository
import dev.sertan.android.ram.core.data.repository.UserSettingsRepository
import dev.sertan.android.ram.core.data.service.speechtotext.RamSpeechToTextService
import dev.sertan.android.ram.core.data.service.speechtotext.SpeechToTextService
import dev.sertan.android.ram.core.data.service.texttospeech.RamTextToSpeechService
import dev.sertan.android.ram.core.data.service.texttospeech.TextToSpeechService

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    abstract fun bindUserSettingsRepository(
        userSettingsRepository: RamUserSettingsRepository
    ): UserSettingsRepository

    @Binds
    abstract fun bindTextToSpeechService(service: RamTextToSpeechService): TextToSpeechService

    @Binds
    abstract fun bindSpeechToTextService(service: RamSpeechToTextService): SpeechToTextService
}
