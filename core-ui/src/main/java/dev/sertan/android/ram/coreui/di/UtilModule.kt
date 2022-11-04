/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.di

import android.content.Context
import android.speech.tts.TextToSpeech
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.util.Locale

@Module
@InstallIn(ActivityRetainedComponent::class)
object UtilModule {

    @Provides
    @ActivityRetainedScoped
    fun provideTextToSpeechService(@ApplicationContext context: Context): TextToSpeech =
        TextToSpeech(context) {}.apply { language = Locale.getDefault() }
}
