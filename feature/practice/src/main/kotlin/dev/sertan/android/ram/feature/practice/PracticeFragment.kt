/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.practice

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.core.ui.util.hide
import dev.sertan.android.ram.core.ui.util.navTo
import dev.sertan.android.ram.core.ui.util.playCorrectSound
import dev.sertan.android.ram.core.ui.util.playNegativeSound
import dev.sertan.android.ram.core.ui.util.popBackStack
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.show
import dev.sertan.android.ram.core.ui.util.viewBinding
import dev.sertan.android.ram.feature.practice.PracticeFragmentDirections.Companion.actionPracticeFragmentToResultFragment
import dev.sertan.android.ram.feature.practice.databinding.FragmentPracticeBinding
import dev.sertan.android.ram.feature.question.ui.adapter.QuestionMaterialAdapter
import dev.sertan.android.ram.feature.question.ui.adapter.QuestionMaterialViewHolder
import dev.sertan.android.ram.feature.question.ui.model.Material
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class PracticeFragment :
    Fragment(R.layout.fragment_practice),
    QuestionMaterialViewHolder.MaterialListener {

    private val viewModel by viewModels<PracticeViewModel>()
    private val binding by viewBinding(FragmentPracticeBinding::bind)

    @Inject
    lateinit var adapter: QuestionMaterialAdapter

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect { uiState ->
            with(binding) {
                contentTextView.text = uiState.question?.content
                finishButton.isInvisible = !uiState.isFinishButtonVisible
                nextButton.isInvisible = !uiState.isForwardButtonVisible
                adapter.submitList(uiState.question?.materials)
                uiState.isEmptyListMessageVisible?.let { changeContentVisibility(isVisible = !it) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.listener = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
        repeatOnLifecycleStarted(onLifecycleStarted)
    }

    private fun setUpComponents(): Unit = with(binding) {
        materialsRecyclerView.adapter = adapter
        nextButton.setOnClickListener {
            answerStateImageView.hide()
            viewModel.goToNextQuestion()
        }
        contentTextView.setOnClickListener { viewModel.speakCurrentQuestionContent() }
        exitButton.setOnClickListener { popBackStack() }
        finishButton.setOnClickListener {
            navTo(actionPracticeFragmentToResultFragment(score = viewModel.score))
        }
    }

    override fun onMaterialClicked(material: Material, isCorrect: Boolean) {
        val bgResId = playAnswerSoundAndGetIconRes(isCorrect)
        binding.answerStateImageView.setImageResource(bgResId)
        binding.answerStateImageView.show()

        viewModel.updateScore(isCorrect)
        viewModel.isValidationActive = false
    }

    private fun playAnswerSoundAndGetIconRes(isCorrect: Boolean): Int = if (isCorrect) {
        playCorrectSound()
        dev.sertan.android.ram.core.ui.R.drawable.ic_answer_correct
    } else {
        playNegativeSound()
        dev.sertan.android.ram.core.ui.R.drawable.ic_answer_wrong
    }

    override fun isMaterialCorrect(material: Material): Boolean? =
        viewModel.isMaterialCorrect(material)

    override fun onStop() {
        super.onStop()
        viewModel.stopSpeech()
    }

    private fun changeContentVisibility(isVisible: Boolean): Unit = with(binding) {
        contentGroup.isVisible = isVisible
        emptyListMessageTextView.isVisible = !isVisible
    }
}
