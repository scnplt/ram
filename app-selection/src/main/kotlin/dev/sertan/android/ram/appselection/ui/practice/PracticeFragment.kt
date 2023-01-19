/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.ui.practice

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appselection.R
import dev.sertan.android.ram.appselection.databinding.FragmentPracticeBinding
import dev.sertan.android.ram.appselection.ui.practice.PracticeFragmentDirections.Companion.actionPracticeFragmentToResultFragment
import dev.sertan.android.ram.appselection.ui.practice.adapter.QuestionAdapter
import dev.sertan.android.ram.appselection.ui.practice.adapter.QuestionViewHolder
import dev.sertan.android.ram.core.ui.util.navTo
import dev.sertan.android.ram.core.ui.util.playCorrectSound
import dev.sertan.android.ram.core.ui.util.playNegativeSound
import dev.sertan.android.ram.core.ui.util.popBackStack
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.viewBinding
import dev.sertan.android.ram.feature.material.ui.model.Material
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class PracticeFragment :
    Fragment(R.layout.fragment_practice),
    QuestionViewHolder.Listener {

    private val viewModel by viewModels<PracticeViewModel>()
    private val binding by viewBinding(FragmentPracticeBinding::bind)

    @Inject
    lateinit var adapter: QuestionAdapter

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect {
            with(binding) {
                contentTextView.text = it.question?.content
                finishButton.isInvisible = !it.isFinishButtonVisible
                forwardButton.isInvisible = !it.isForwardButtonVisible
                adapter.submitList(it.question?.materials)
                it.isEmptyListMessageVisible?.let { changeContentVisibility(isVisible = !it) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.listener = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            materialsRecyclerView.adapter = adapter
            forwardButton.setOnClickListener { viewModel.goToNextQuestion() }
            contentTextView.setOnClickListener { viewModel.speakCurrentQuestionContent() }
            exitButton.setOnClickListener { popBackStack() }
            finishButton.setOnClickListener {
                navTo(actionPracticeFragmentToResultFragment(score = viewModel.score))
            }
        }
        repeatOnLifecycleStarted(onLifecycleStarted)
    }

    override fun onMaterialClicked(material: Material) {
        viewModel.isValidationActive = false
    }

    override fun isMaterialCorrect(material: Material): Boolean? =
        viewModel.isMaterialCorrect(material)

    override fun onCorrectMaterialClicked(material: Material) {
        context?.playCorrectSound()
        viewModel.setAnswerState(isCorrect = true)
    }

    override fun onWrongMaterialClicked(material: Material) {
        context?.playNegativeSound()
        viewModel.setAnswerState(isCorrect = false)
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSpeech()
    }

    private fun changeContentVisibility(isVisible: Boolean) = with(binding) {
        contentScrollView.isVisible = isVisible
        materialsRecyclerView.isVisible = isVisible
        forwardButton.isVisible = isVisible
        finishButton.isVisible = isVisible
        exitButton.isVisible = isVisible
        emptyListMessageTextView.isVisible = !isVisible
    }
}
