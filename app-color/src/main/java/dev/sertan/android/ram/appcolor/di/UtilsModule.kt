/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.di

import android.content.Context
import android.speech.tts.TextToSpeech
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dev.sertan.android.ram.appcolor.R
import dev.sertan.android.ram.coreui.util.showToast
import java.util.Locale

@Module
@InstallIn(ActivityComponent::class)
internal object UtilsModule {

    @Provides
    @ActivityScoped
    fun provideTextToSpeech(@ActivityContext context: Context): TextToSpeech = with(context) {
        TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) return@TextToSpeech
            showToast(getString(R.string.device_lang_not_supported))
        }.apply { language = Locale.getDefault() }
    }
}
