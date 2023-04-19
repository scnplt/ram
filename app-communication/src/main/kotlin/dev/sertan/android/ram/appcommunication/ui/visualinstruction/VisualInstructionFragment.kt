/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.visualinstruction

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.sertan.android.ram.appcommunication.R
import dev.sertan.android.ram.appcommunication.databinding.FragmentVisualInstructionBinding
import dev.sertan.android.ram.core.ui.util.extension.playCorrectSound
import dev.sertan.android.ram.core.ui.util.extension.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.extension.viewBinding
import kotlinx.coroutines.CoroutineScope

internal class VisualInstructionFragment : Fragment(R.layout.fragment_visual_instruction) {

    private val binding by viewBinding(FragmentVisualInstructionBinding::bind)
    private val viewModel by viewModels<VisualInstructionViewModel>()

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect {
            with(binding) {
                catchButton.isInvisible = it.isCatchButtonInvisible
                startButton.isInvisible = it.isStartButtonInvisible
            }

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
            catchButton.setOnClickListener {
                if (startButton.isVisible) return@setOnClickListener
                viewModel.catch()
                playCorrectSound()
            }
        }
    }
}
