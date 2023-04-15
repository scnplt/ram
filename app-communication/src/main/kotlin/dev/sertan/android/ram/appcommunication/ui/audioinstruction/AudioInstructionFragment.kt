/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.audioinstruction

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.sertan.android.ram.appcommunication.R
import dev.sertan.android.ram.appcommunication.databinding.FragmentAudioInstructionBinding
import dev.sertan.android.ram.core.ui.util.playSound
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.setIconTint
import dev.sertan.android.ram.core.ui.util.viewBinding
import kotlinx.coroutines.CoroutineScope

internal class AudioInstructionFragment : Fragment(R.layout.fragment_audio_instruction) {

    private val binding by viewBinding(FragmentAudioInstructionBinding::bind)
    private val viewModel by viewModels<AudioInstructionViewModel>()

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect {
            with(binding) {
                catchButton.isEnabled = it.isCatchButtonEnabled
                catchButton.setIconTint(it.catchButtonTint)
                startButton.isInvisible = it.isStartButtonInvisible
            }

            if (it.isRingtonePlaying.getDataIfNotHandled() == true) playSound(R.raw.beep)

            if (it.isFinished) {
                Toast.makeText(requireContext(), "Finished", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repeatOnLifecycleStarted(onLifecycleStarted)
        with(binding) {
            startButton.setOnClickListener { viewModel.startGame() }
            catchButton.setOnClickListener { viewModel.catch() }
        }
    }
}
