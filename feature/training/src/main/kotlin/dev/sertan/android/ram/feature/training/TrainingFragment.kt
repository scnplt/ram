/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.core.ui.util.loadFromUrl
import dev.sertan.android.ram.core.ui.util.popBackStack
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.viewBinding
import dev.sertan.android.ram.feature.training.databinding.FragmentTrainingBinding
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class TrainingFragment : Fragment(R.layout.fragment_training) {
    private val binding by viewBinding(FragmentTrainingBinding::bind)
    private val viewModel by viewModels<TrainingViewModel>()

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect {
            with(binding) {
                it.material?.run {
                    setAttributionView(attribution)
                    descriptionTextView.text = description
                    mediaImageView.contentDescription = description
                    mediaImageView.loadFromUrl(mediaUrl)
                }
                backButton.isInvisible = !it.isBackButtonVisible
                forwardButton.isInvisible = !it.isForwardButtonVisible
                finishButton.isInvisible = !it.isFinishButtonVisible
                progressIndicator.progress = it.progress
                it.isEmptyListMessageVisible?.let { changeContentVisibility(isVisible = !it) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getBoolean(SHUFFLE_KEY)?.let { savedInstanceState?.putBoolean(SHUFFLE_KEY, it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            with(viewModel) {
                forwardButton.setOnClickListener { goToNextMaterial() }
                backButton.setOnClickListener { goToPreviousMaterial() }
                exitButton.setOnClickListener { popBackStack() }
                materialConstraintLayout.setOnClickListener { speakCurrentMaterialDescription() }
                finishButton.setOnClickListener { popBackStack(KEY_FINISHED, true) }
            }
        }
        repeatOnLifecycleStarted(onLifecycleStarted)
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopSpeech()
    }

    private fun changeContentVisibility(isVisible: Boolean) = with(binding) {
        contentGroup.isVisible = isVisible
        emptyListMessageTextView.isVisible = !isVisible
    }

    private fun setAttributionView(attribution: String?): Unit = with(binding) {
        attributionTextView.isGone = attribution.isNullOrEmpty().also { if (it) return@with }
        attributionTextView.text = getString(
            dev.sertan.android.ram.core.ui.R.string.this_icon_was_created_by,
            attribution
        )
    }

    companion object {
        const val KEY_FINISHED = "navResult - finishButton"
        const val SHUFFLE_KEY = "shuffle"
    }
}
