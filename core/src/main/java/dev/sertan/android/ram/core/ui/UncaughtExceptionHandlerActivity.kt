/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.sertan.android.ram.core.R
import dev.sertan.android.ram.core.databinding.ActivityUncaughtExceptionHandlerBinding
import dev.sertan.android.ram.core.util.extension.viewBinding

class UncaughtExceptionHandlerActivity :
    AppCompatActivity(R.layout.activity_uncaught_exception_handler) {

    private val binding by viewBinding(ActivityUncaughtExceptionHandlerBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            errorMessageTextView.text = intent.getStringExtra(EXTRA_EXCEPTION_MESSAGE)
            closeAppButton.setOnClickListener { finish() }
            reportErrorButton.setOnClickListener {
                // TASK: report the error
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_EXCEPTION_MESSAGE = "intent extra - exception message"
    }
}
