/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.matching

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appmemory.R
import dev.sertan.android.ram.appmemory.databinding.FragmentMatchingBinding
import dev.sertan.android.ram.core.ui.util.extension.hide
import dev.sertan.android.ram.core.ui.util.extension.playCorrectSound
import dev.sertan.android.ram.core.ui.util.extension.playNegativeSound
import dev.sertan.android.ram.core.ui.util.extension.popBackStack
import dev.sertan.android.ram.core.ui.util.extension.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.extension.viewBinding
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
internal class MatchingFragment : Fragment(R.layout.fragment_matching) {

    private val viewModel by viewModels<MatchingViewModel>()
    private val binding by viewBinding(FragmentMatchingBinding::bind)

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect { uiState ->
            with(binding) {
                uiState.matchingQuestion?.apply {
                    contentTextView.text = content
//                    mediaImageView.loadFromUrl(contentMaterial.mediaUrl)
//                    setAttributionView(contentMaterial.attribution)
//                    adapter.submitList(materials)
                }
                finishButton.isInvisible = !uiState.isFinishButtonVisible
                nextButton.isInvisible = !uiState.isForwardButtonVisible
                uiState.isEmptyListMessageVisible?.let { changeContentVisibility(isVisible = !it) }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
        repeatOnLifecycleStarted(onLifecycleStarted)
    }

    private fun setUpComponents(): Unit = with(binding) {
//      materialsRecyclerView.adapter = adapter
        nextButton.setOnClickListener {
            answerStateImageView.hide()
            viewModel.goToNextQuestion()
        }
        contentTextView.setOnClickListener { viewModel.speakCurrentQuestionContent() }
        exitButton.setOnClickListener { popBackStack() }
        finishButton.setOnClickListener { popBackStack() }
    }

    private fun playAnswerSoundAndGetIconRes(isCorrect: Boolean): Int = if (isCorrect) {
        playCorrectSound()
        dev.sertan.android.ram.core.ui.R.drawable.ic_answer_correct
    } else {
        playNegativeSound()
        dev.sertan.android.ram.core.ui.R.drawable.ic_answer_wrong
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSpeech()
    }

    private fun changeContentVisibility(isVisible: Boolean): Unit = with(binding) {
        contentGroup.isVisible = isVisible
        emptyListMessageTextView.isVisible = !isVisible
    }
}
