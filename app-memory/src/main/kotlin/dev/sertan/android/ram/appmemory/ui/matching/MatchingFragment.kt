/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.ui.matching

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appmemory.R
import dev.sertan.android.ram.appmemory.databinding.FragmentMatchingBinding
import dev.sertan.android.ram.appmemory.ui.matching.adapter.MatchingListAdapter
import dev.sertan.android.ram.appmemory.ui.matching.adapter.MaterialItemListener
import dev.sertan.android.ram.core.ui.util.extension.hide
import dev.sertan.android.ram.core.ui.util.extension.playAnswerSoundAndGetStateIconRes
import dev.sertan.android.ram.core.ui.util.extension.popBackStack
import dev.sertan.android.ram.core.ui.util.extension.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.extension.show
import dev.sertan.android.ram.core.ui.util.extension.viewBinding
import dev.sertan.android.ram.feature.material.ui.Material
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
internal class MatchingFragment :
    Fragment(R.layout.fragment_matching),
    MaterialItemListener {

    private val viewModel by viewModels<MatchingViewModel>()
    private val binding by viewBinding(FragmentMatchingBinding::bind)

    @Inject
    lateinit var adapter: MatchingListAdapter

    private val layoutManager by lazy {
        GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = if (position == 0) 2 else 1
            }
        }
    }

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect { uiState ->
            with(binding) {
                adapter.submitList(uiState.contentItems)
                nextButton.isInvisible = uiState.isNextButtonInvisible
                finishButton.isVisible = uiState.isFinishButtonVisible
                uiState.isEmptyListMessageVisible?.let { setContentVisibility(isVisible = !it) }
            }
        }
    }

    private fun setContentVisibility(isVisible: Boolean): Unit = with(binding) {
        contentRecyclerView.isVisible = isVisible
        nextButton.isVisible = isVisible
        finishButton.isVisible = isVisible
        emptyListMessageTextView.isVisible = !isVisible
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

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpComponents(): Unit = with(binding) {
        contentRecyclerView.layoutManager = layoutManager
        contentRecyclerView.adapter = adapter
        attributionInfoButton.setOnClickListener { TODO("Not yet implemented") }
        nextButton.setOnClickListener {
            answerStateImageView.hide()
            viewModel.goToNextQuestion()
            adapter.notifyDataSetChanged()
        }
        finishButton.setOnClickListener { popBackStack() }
        exitButton.setOnClickListener { popBackStack() }
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSpeech()
    }

    override fun onMaterialClicked(material: Material) {
        val isCorrect = viewModel.checkAnswerState(selectedMaterial = material) ?: return
        val bgResId = playAnswerSoundAndGetStateIconRes(isCorrect)
        with(binding) {
            answerStateImageView.setImageResource(bgResId)
            answerStateImageView.show()
            viewModel.isValidationActive = false
        }
    }
}
