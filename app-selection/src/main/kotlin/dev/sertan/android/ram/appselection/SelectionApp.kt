/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.domain.usecase.VoiceSupportUseCase
import dev.sertan.android.ram.core.domain.worker.UpdateLocalDataWorker
import dev.sertan.android.ram.core.ui.RamApplication
import javax.inject.Inject

@HiltAndroidApp
internal class SelectionApp : RamApplication(), Configuration.Provider {

    @Inject
    lateinit var voiceSupportUseCase: VoiceSupportUseCase

    @Inject
    lateinit var ramLogger: RamLogger

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        ramLogger.debugInit()
        UpdateLocalDataWorker.start(this)
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder().setWorkerFactory(workerFactory).build()
}
