/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.common.log

import dev.sertan.android.ram.core.common.BuildConfig
import javax.inject.Inject
import timber.log.Timber

internal class TimberRamLogger @Inject constructor() : RamLogger {

    override fun debugInit() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun e(exception: Throwable): Unit = Timber.e(exception)

    override fun i(message: String?): Unit = Timber.i(message)

    override fun d(message: String?): Unit = Timber.d(message)

    override fun v(message: String?): Unit = Timber.v(message)

    override fun w(message: String?): Unit = Timber.w(message)
}
