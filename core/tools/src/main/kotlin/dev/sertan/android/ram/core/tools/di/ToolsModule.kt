/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.tools.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.core.tools.speechservice.RamSpeechToTextService
import dev.sertan.android.ram.core.tools.speechservice.RamTextToSpeechService
import dev.sertan.android.ram.core.tools.speechservice.SpeechToTextService
import dev.sertan.android.ram.core.tools.speechservice.TextToSpeechService
import java.util.Locale
import javax.inject.Singleton

internal const val DEFAULT_LANG_TAG = "tr-TR"

@Module
@InstallIn(SingletonComponent::class)
internal object ToolsModule {

    @Provides
    @Singleton
    fun provideTextToSpeechService(service: RamTextToSpeechService): TextToSpeechService = service

    @Provides
    @Singleton
    fun provideSpeechToTextService(service: RamSpeechToTextService): SpeechToTextService = service

    @Provides
    @Singleton
    fun provideLocale(): Locale = Locale(DEFAULT_LANG_TAG)
}
