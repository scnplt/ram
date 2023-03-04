/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.ui.counting

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appnumber.R
import dev.sertan.android.ram.appnumber.databinding.FragmentCountingBinding
import dev.sertan.android.ram.core.ui.util.playCorrectSound
import dev.sertan.android.ram.core.ui.util.playNegativeSound
import dev.sertan.android.ram.core.ui.util.popBackStack
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.viewBinding
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
internal class CountingFragment : Fragment(R.layout.fragment_counting) {
    private val binding by viewBinding(FragmentCountingBinding::bind)
    private val viewModel by viewModels<CountingViewModel>()

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect {
            binding.contentLayout.isInvisible = it !is CountingUiState.Success
            if (it == CountingUiState.Failure) showErrorMessage()
            if (it is CountingUiState.Success) {
                showNumber(it)
                viewModel.takeIf { binding.micButton.isEnabled }
                    ?.speak(
                        text = getString(
                            R.string.current_number_what_the_next,
                            it.number,
                            it.step
                        )
                    )
            }
        }
    }

    private val numberListener = object : CountingViewModel.NumberListener {

        override fun onStart() {
            binding.micButton.isEnabled = false
            binding.progressIndicator.show()
        }

        override fun onCorrect(newNumber: Int, step: Int) {
            context?.playCorrectSound()
        }

        override fun onWrong() {
            context?.playNegativeSound()
        }

        override fun onComplete(): Unit = stopLoadingAnimation()

        override fun onError(errorCode: Int): Unit = stopLoadingAnimation()

        private fun stopLoadingAnimation() {
            binding.progressIndicator.hide()
            binding.micButton.isEnabled = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        repeatOnLifecycleStarted(onLifecycleStarted)
    }

    private fun setUpListeners(): Unit = with(binding) {
        viewModel.listener = numberListener
        micButton.setOnClickListener { viewModel.listenToNumber() }
        nextSectionButton.setOnClickListener { viewModel.nextSection() }
        finishButton.setOnClickListener { popBackStack() }
    }

    private fun showErrorMessage() {
        // TASK: Refactor this function and use the custom dialog for showing an error message
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }

    private fun showNumber(state: CountingUiState.Success): Unit = with(binding) {
        currentNumberTextView.text = state.number.toString()
        descriptionTextView.text = getString(R.string.what_is_the_next_number, state.step)
        micButton.isEnabled = state.isMicButtonEnabled
        nextSectionButton.isInvisible = !state.isNextSectionButtonVisible
        finishButton.isInvisible = !state.isFinishButtonVisible
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSpeech()
    }
}
