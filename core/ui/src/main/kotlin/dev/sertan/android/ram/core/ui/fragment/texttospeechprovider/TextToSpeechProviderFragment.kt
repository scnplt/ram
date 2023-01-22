/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.fragment.texttospeechprovider

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class TextToSpeechProviderFragment(
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId), TextToSpeechProvider {

    @Inject
    lateinit var textToSpeechUseCase: TextToSpeechUseCase

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        textToSpeechUseCase.getVoiceSupportStateStream()
            .collect { isActive -> onTextToSpeechStateChanged(isActive) }
    }

    private var changeVoiceSupportJob: Job? = null

    override fun changeTextToSpeechState() {
        changeVoiceSupportJob?.cancel()
        changeVoiceSupportJob = lifecycleScope.launch { textToSpeechUseCase.change() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repeatOnLifecycleStarted(onLifecycleStarted)
    }
}
