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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appselection.R
import dev.sertan.android.ram.appselection.databinding.FragmentPracticeBinding
import dev.sertan.android.ram.appselection.ui.model.Material
import dev.sertan.android.ram.appselection.ui.practice.PracticeFragmentDirections.Companion.actionPracticeFragmentToResultFragment
import dev.sertan.android.ram.appselection.ui.practice.adapter.QuestionAdapter
import dev.sertan.android.ram.appselection.ui.practice.adapter.QuestionViewHolder
import dev.sertan.android.ram.core.ui.util.extension.navigateTo
import dev.sertan.android.ram.core.ui.util.extension.playSound
import dev.sertan.android.ram.core.ui.util.extension.popBackStack
import dev.sertan.android.ram.core.ui.util.extension.viewBinding
import javax.inject.Inject
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class PracticeFragment :
    Fragment(R.layout.fragment_practice),
    QuestionViewHolder.Listener {

    private val viewModel by viewModels<PracticeViewModel>()
    private val binding by viewBinding(FragmentPracticeBinding::bind)

    @Inject
    lateinit var adapter: QuestionAdapter

    private val uiStateFlowCollector = FlowCollector<PracticeUiState> {
        with(binding) {
            contentTextView.text = it.question?.content
            finishButton.isInvisible = !it.isFinishButtonVisible
            nextButton.isInvisible = !it.isForwardButtonVisible
            adapter.submitList(it.question?.materials)
            it.isEmptyListMessageVisible?.let { changeContentVisibility(isVisible = !it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.listener = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(uiStateFlowCollector)
            }
        }
    }

    private fun setUpComponents(): Unit = with(binding) {
        materialsRecyclerView.adapter = adapter
        finishButton.setOnClickListener {
            navigateTo(actionPracticeFragmentToResultFragment(score = viewModel.score))
        }
        nextButton.setOnClickListener { viewModel.goToNextQuestion() }
        contentTextView.setOnClickListener { viewModel.speakCurrentQuestionContent() }
        exitButton.setOnClickListener { popBackStack() }
    }

    override fun onMaterialClicked(material: Material) {
        viewModel.isValidationActive = false
    }

    override fun isMaterialCorrect(material: Material): Boolean? =
        viewModel.isMaterialCorrect(material)

    override fun onCorrectMaterialClicked(material: Material) {
        context?.playSound(dev.sertan.android.ram.core.ui.R.raw.correct)
        viewModel.setAnswerState(isCorrect = true)
    }

    override fun onWrongMaterialClicked(material: Material) {
        context?.playSound(dev.sertan.android.ram.core.ui.R.raw.negative)
        viewModel.setAnswerState(isCorrect = false)
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSpeech()
    }

    private fun changeContentVisibility(isVisible: Boolean) = with(binding) {
        contentGroup.isVisible = isVisible
        emptyListMessageTextView.isVisible = !isVisible
        exitButton.isVisible = !isVisible
    }
}
