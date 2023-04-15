/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.objectrecognition

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcommunication.R
import dev.sertan.android.ram.appcommunication.databinding.FragmentObjectRecognitionBinding
import dev.sertan.android.ram.core.ui.util.loadFromUrl
import dev.sertan.android.ram.core.ui.util.playCorrectSound
import dev.sertan.android.ram.core.ui.util.playNegativeSound
import dev.sertan.android.ram.core.ui.util.popBackStack
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.show
import dev.sertan.android.ram.core.ui.util.viewBinding
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
internal class ObjectRecognitionFragment : Fragment(R.layout.fragment_object_recognition) {

    private val binding by viewBinding(FragmentObjectRecognitionBinding::bind)
    private val viewModel by viewModels<ObjectRecognitionViewModel>()

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect {
            with(binding) {
                it.material?.let { material ->
                    setAttributionView(attribution = material.attribution)
                    mediaImageView.loadFromUrl(material.mediaUrl)
                }
                contentGroup.isVisible = !it.isEmptyListMessageVisible
                emptyListMessageTextView.isVisible = it.isEmptyListMessageVisible
                micButton.isInvisible = !it.isMicButtonVisible
                forwardButton.isInvisible = it.isForwardButtonInvisible
                finishButton.isVisible = it.isFinishButtonVisible
                backButton.isInvisible = it.isBackButtonInvisible
            }
        }
    }

    private val micListener = object : ObjectRecognitionViewModel.MicListener {

        override fun onStart(): Unit = with(binding) {
            micButton.isInvisible = true
            progressIndicator.show()
        }

        override fun onCorrect(): Unit = playCorrectSound()

        override fun onWrong(): Unit = playNegativeSound()

        override fun onComplete(): Unit = stopLoadingAnimation()

        override fun onError(errorCode: Int): Unit = stopLoadingAnimation()

        private fun stopLoadingAnimation(): Unit = with(binding) {
            progressIndicator.hide()
            micButton.show()
        }
    }

    private fun setAttributionView(attribution: String?): Unit = with(binding) {
        attributionTextView.isGone = attribution.isNullOrEmpty().also { if (it) return@with }
        attributionTextView.text = getString(
            dev.sertan.android.ram.core.ui.R.string.this_icon_was_created_by,
            attribution
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
        repeatOnLifecycleStarted(onLifecycleStarted)
    }

    private fun setUpComponents(): Unit = with(binding) {
        viewModel.listener = micListener
        micButton.setOnClickListener { viewModel.listenMic() }
        exitButton.setOnClickListener { popBackStack() }
        forwardButton.setOnClickListener { viewModel.goToNextObject() }
        backButton.setOnClickListener { viewModel.goToPreviousObject() }
        finishButton.setOnClickListener { popBackStack() }
    }
}
