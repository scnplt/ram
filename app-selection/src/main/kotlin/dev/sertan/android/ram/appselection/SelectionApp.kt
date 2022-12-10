/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection

import dagger.hilt.android.HiltAndroidApp
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.domain.usecase.RefreshLocalDataUseCase
import dev.sertan.android.ram.core.domain.usecase.VoiceSupportUseCase
import dev.sertan.android.ram.core.ui.RamApplication
import javax.inject.Inject

@HiltAndroidApp
internal class SelectionApp : RamApplication() {

    @Inject
    lateinit var voiceSupportUseCase: VoiceSupportUseCase

    @Inject
    lateinit var ramLogger: RamLogger

    @Inject
    lateinit var refreshLocalDataUseCase: RefreshLocalDataUseCase

    override fun onCreate() {
        super.onCreate()
        ramLogger.debugInit()
    }
}
