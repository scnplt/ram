/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.gapfilling

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appmemory.R
import dev.sertan.android.ram.appmemory.databinding.FragmentGapFillingBinding
import dev.sertan.android.ram.core.ui.util.extension.hide
import dev.sertan.android.ram.core.ui.util.extension.loadFromUrl
import dev.sertan.android.ram.core.ui.util.extension.playAnswerSoundAndGetStateIconRes
import dev.sertan.android.ram.core.ui.util.extension.popBackStack
import dev.sertan.android.ram.core.ui.util.extension.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.extension.show
import dev.sertan.android.ram.core.ui.util.extension.viewBinding
import dev.sertan.android.ram.feature.material.ui.Material
import dev.sertan.android.ram.feature.training.ui.practice.adapter.QuestionMaterialAdapter
import dev.sertan.android.ram.feature.training.ui.practice.adapter.QuestionMaterialViewHolder
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
internal class GapFillingFragment :
    Fragment(R.layout.fragment_gap_filling),
    QuestionMaterialViewHolder.MaterialListener {

    private val viewModel by viewModels<GapFillingViewModel>()
    private val binding by viewBinding(FragmentGapFillingBinding::bind)

    @Inject
    lateinit var adapter: QuestionMaterialAdapter

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect { uiState ->
            with(binding) {
                uiState.gapFillingQuestion?.apply {
                    contentTextView.text = content
                    mediaImageView.loadFromUrl(contentMaterial.mediaUrl)
                    setAttributionView(contentMaterial.attribution)
                    adapter.submitList(materials)
                }
                finishButton.isInvisible = !uiState.isFinishButtonVisible
                nextButton.isInvisible = !uiState.isForwardButtonVisible
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
        finishButton.setOnClickListener { popBackStack() }
    }

    override fun onMaterialClicked(material: Material, isCorrect: Boolean) {
        val bgResId = playAnswerSoundAndGetStateIconRes(isCorrect)
        binding.answerStateImageView.setImageResource(bgResId)
        binding.answerStateImageView.show()
        viewModel.isValidationActive = false
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

    private fun setAttributionView(attribution: String?): Unit = with(binding) {
        attributionTextView.isInvisible = attribution == null
        attributionTextView.text = getString(
            dev.sertan.android.ram.core.ui.R.string.this_icon_was_created_by,
            attribution
        )
    }
}
