/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor

import dagger.hilt.android.HiltAndroidApp
import dev.sertan.android.ram.appcolor.domain.worker.UpdateLocalMaterialsWorker
import dev.sertan.android.ram.appcolor.domain.worker.UpdateLocalQuestionsWorker
import dev.sertan.android.ram.core.RamApplication

@HiltAndroidApp
internal class ColorApp : RamApplication() {

    override fun onCreate() {
        super.onCreate()
        UpdateLocalMaterialsWorker.uniqueStart(applicationContext)
        UpdateLocalQuestionsWorker.uniqueStart(applicationContext)
    }
}
