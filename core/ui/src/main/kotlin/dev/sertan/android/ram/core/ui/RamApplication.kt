/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import java.security.Security
import javax.inject.Inject
import org.conscrypt.Conscrypt

abstract class RamApplication : Application() {

    @Inject
    lateinit var textToSpeechUseCase: TextToSpeechUseCase

    @Inject
    lateinit var ramLogger: RamLogger

    override fun onCreate() {
        super.onCreate()
        ramLogger.debugInit()
        Security.insertProviderAt(Conscrypt.newProvider(), 1)
        // TASK: Check internet connection and show a message if no connection
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (BuildConfig.DEBUG) MultiDex.install(this)
    }
}
