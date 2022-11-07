/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.practice

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcolor.R
import dev.sertan.android.ram.appcolor.databinding.FragmentPracticeBinding
import dev.sertan.android.ram.appcolor.screen.practice.adapter.QuestionMaterialsAdapter
import dev.sertan.android.ram.appcolor.screen.practice.adapter.QuestionMaterialsViewHolder
import dev.sertan.android.ram.coreui.model.Material
import dev.sertan.android.ram.coreui.util.extension.playSound
import dev.sertan.android.ram.coreui.util.extension.showToast
import dev.sertan.android.ram.coreui.util.extension.viewBinding
import javax.inject.Inject
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class PracticeFragment : Fragment(R.layout.fragment_practice),
    QuestionMaterialsViewHolder.QuestionMaterialListener {

    private val viewModel by viewModels<PracticeViewModel>()
    private val binding by viewBinding(FragmentPracticeBinding::bind)

    @Inject
    lateinit var adapter: QuestionMaterialsAdapter

    private val uiStateFlowCollector = FlowCollector<PracticeUiState> {
        with(binding) {
            contentTextView.text = it.question?.content
            finishButton.isInvisible = !it.isFinishButtonVisible
            nextButton.isInvisible = !it.isForwardButtonVisible
            adapter.submitList(it.question?.materials)
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
        finishButton.setOnClickListener { showToast("Not yet implemented") }
        nextButton.setOnClickListener { viewModel.goToNextQuestion() }
        contentTextView.setOnClickListener { viewModel.speakCurrentQuestionContent() }
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSpeech()
    }

    override fun onMaterialClicked(material: Material): Unit =
        viewModel.setAnswerValidation(isActive = false)

    override fun isMaterialCorrect(material: Material): Boolean? =
        viewModel.isMaterialCorrect(material)

    override fun onCorrectMaterialClicked(material: Material) {
        context?.playSound(dev.sertan.android.ram.coreui.R.raw.correct)
    }

    override fun onWrongMaterialClicked(material: Material) {
        context?.playSound(dev.sertan.android.ram.coreui.R.raw.negative)
    }
}
