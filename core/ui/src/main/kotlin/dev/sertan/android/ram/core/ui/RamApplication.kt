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
import android.content.Intent
import dev.sertan.android.ram.core.ui.UncaughtExceptionHandlerActivity.Companion.EXTRA_EXCEPTION_MESSAGE

abstract class RamApplication : Application() {

    private val uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, throwable ->
        val intent = Intent(this, UncaughtExceptionHandlerActivity::class.java).apply {
            putExtra(EXTRA_EXCEPTION_MESSAGE, throwable.localizedMessage)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler)
        // TASK: Check internet connection and show a message if no connection
    }
}
