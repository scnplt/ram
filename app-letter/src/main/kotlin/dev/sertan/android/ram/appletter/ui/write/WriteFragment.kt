/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appletter.ui.write

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appletter.R
import dev.sertan.android.ram.appletter.databinding.FragmentWriteBinding
import dev.sertan.android.ram.core.ui.util.playSound
import dev.sertan.android.ram.core.ui.util.popBackStack
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.viewBinding
import kotlinx.coroutines.flow.FlowCollector

@AndroidEntryPoint
internal class WriteFragment : Fragment(R.layout.fragment_write) {
    private val binding by viewBinding(FragmentWriteBinding::bind)
    private val viewModel by viewModels<WriteViewModel>()

    private val uiStateFlowCollector = FlowCollector<WriteUiState> {
        with(binding) {
            nextButton.isVisible = it.isNextButtonVisible
            finishButton.isVisible = it.isFinishButtonVisible
        }
    }

    private val answerListener = object : WriteViewModel.AnswerListener {

        override fun onCorrect() {
            requireContext().playSound(dev.sertan.android.ram.core.ui.R.raw.correct)
            binding.inputEditText.text?.clear()
        }

        override fun onWrong(): Unit =
            requireContext().playSound(dev.sertan.android.ram.core.ui.R.raw.negative)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
        viewModel.listener = answerListener
        repeatOnLifecycleStarted { viewModel.uiState.collect(uiStateFlowCollector) }
    }

    private fun setUpComponents(): Unit = with(binding) {
        with(viewModel) {
            listenAgainButton.setOnClickListener { speakCurrentLetter() }
            nextButton.setOnClickListener {
                nextLetterIfAnswerCorrect(input = inputEditText.text.toString())
            }
            finishButton.setOnClickListener {
                if (checkInput(input = inputEditText.text.toString())) popBackStack()
            }
        }
    }
}
