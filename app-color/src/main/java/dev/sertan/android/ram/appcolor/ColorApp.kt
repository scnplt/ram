/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.multidex.MultiDex
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import dev.sertan.android.ram.coredomain.worker.UpdateLocalMaterialsWorker
import dev.sertan.android.ram.coredomain.worker.UpdateLocalQuestionsWorker
import javax.inject.Inject
import timber.log.Timber

@HiltAndroidApp
internal class ColorApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        UpdateLocalMaterialsWorker.uniqueStart(applicationContext)
        UpdateLocalQuestionsWorker.uniqueStart(applicationContext)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
