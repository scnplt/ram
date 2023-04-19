/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.bodyparts

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcommunication.databinding.FragmentBodyPartsBinding
import dev.sertan.android.ram.core.ui.R
import dev.sertan.android.ram.core.ui.util.extension.hide
import dev.sertan.android.ram.core.ui.util.extension.playCorrectSound
import dev.sertan.android.ram.core.ui.util.extension.playNegativeSound
import dev.sertan.android.ram.core.ui.util.extension.popBackStack
import dev.sertan.android.ram.core.ui.util.extension.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.extension.show
import dev.sertan.android.ram.core.ui.util.extension.viewBinding
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
internal class BodyPartsFragment :
    Fragment(dev.sertan.android.ram.appcommunication.R.layout.fragment_body_parts) {

    private val binding by viewBinding(FragmentBodyPartsBinding::bind)
    private val viewModel by viewModels<BodyPartViewModel>()

    private val attributionText by lazy {
        getString(dev.sertan.android.ram.appcommunication.R.string.man_image_attribution)
    }

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect {
            with(binding) {
                answerStateImageView.hide()
                it.bodyImageResId?.let { imageRes -> bodyImageView.setImageResource(imageRes) }
                it.part?.let { part -> contentTextView.setText(part.contentResId) }
                forwardButton.isInvisible = it.isForwardButtonInvisible
                finishButton.isVisible = it.isFinishButtonVisible
            }
        }
    }

    private val answerListener = BodyPartViewModel.AnswerListener { isCorrect ->
        showAnswerStateImageAndSetIcon(isCorrect)
        if (isCorrect) playCorrectSound() else playNegativeSound()
    }

    private fun showAnswerStateImageAndSetIcon(isCorrect: Boolean) {
        binding.answerStateImageView.apply {
            val bgRes = if (isCorrect) R.drawable.ic_answer_correct else R.drawable.ic_answer_wrong
            setImageResource(bgRes)
        }.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repeatOnLifecycleStarted(onLifecycleStarted)
        viewModel.listener = answerListener
        setUpComponents()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpComponents(): Unit = with(binding) {
        exitButton.setOnClickListener { popBackStack() }
        finishButton.setOnClickListener { viewModel.goToNextPartOrElse { popBackStack() } }
        forwardButton.setOnClickListener { viewModel.goToNextQuestion() }
        attributionTextView.text = getString(R.string.this_icon_was_created_by, attributionText)
        bodyImageView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val xRatio = event.x / v.width
                val yRatio = event.y / v.height
                viewModel.checkTouchPoint(xRatio, yRatio)
                return@setOnTouchListener true
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopSpeech()
    }
}
