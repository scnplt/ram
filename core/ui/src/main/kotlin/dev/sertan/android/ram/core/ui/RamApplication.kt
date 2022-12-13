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
import android.content.Intent
import androidx.hilt.work.HiltWorkerFactory
import androidx.multidex.MultiDex
import androidx.work.Configuration
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.domain.usecase.VoiceSupportUseCase
import dev.sertan.android.ram.core.domain.worker.UpdateLocalDataWorker
import dev.sertan.android.ram.core.ui.UncaughtExceptionHandlerActivity.Companion.EXTRA_EXCEPTION_MESSAGE
import javax.inject.Inject

abstract class RamApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var voiceSupportUseCase: VoiceSupportUseCase

    @Inject
    lateinit var ramLogger: RamLogger

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private val uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, throwable ->
        val intent = Intent(this, UncaughtExceptionHandlerActivity::class.java).apply {
            putExtra(EXTRA_EXCEPTION_MESSAGE, throwable.localizedMessage)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

    override fun onCreate() {
        super.onCreate()
        ramLogger.debugInit()
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler)
        UpdateLocalDataWorker.start(this)
        // TASK: Check internet connection and show a message if no connection
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (BuildConfig.DEBUG) MultiDex.install(this)
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder().setWorkerFactory(workerFactory).build()
}
