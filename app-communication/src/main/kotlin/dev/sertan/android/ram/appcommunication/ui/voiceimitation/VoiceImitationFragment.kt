/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.voiceimitation

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcommunication.R
import dev.sertan.android.ram.appcommunication.databinding.FragmentVoiceImitationBinding
import dev.sertan.android.ram.core.ui.util.playCorrectSound
import dev.sertan.android.ram.core.ui.util.playNegativeSound
import dev.sertan.android.ram.core.ui.util.popBackStack
import dev.sertan.android.ram.core.ui.util.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.show
import dev.sertan.android.ram.core.ui.util.viewBinding
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
internal class VoiceImitationFragment : Fragment(R.layout.fragment_voice_imitation) {

    private val binding by viewBinding(FragmentVoiceImitationBinding::bind)
    private val viewModel by viewModels<VoiceImitationViewModel>()

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        viewModel.uiState.collect {
            with(binding) {
                val isSoundNull = it.sound.isNullOrEmpty()
                currentSoundTextView.text = it.sound.orEmpty()
                contentGroup.isVisible = !isSoundNull
                emptyListMessageTextView.isVisible = isSoundNull
                forwardButton.isInvisible = !it.isForwardButtonVisible
                finishButton.isVisible = it.isFinishButtonVisible
                backButton.isInvisible = it.isBackButtonInvisible
            }
        }
    }

    private val micListener = object : VoiceImitationViewModel.MicListener {

        override fun onStart(): Unit = with(binding) {
            micButton.isInvisible = true
            progressIndicator.show()
        }

        override fun onCorrect(): Unit = playCorrectSound()

        override fun onWrong(): Unit = playNegativeSound()

        override fun onComplete(): Unit = stopLoadingAnimation()

        override fun onError(errorCode: Int): Unit = stopLoadingAnimation()
    }

    private fun stopLoadingAnimation(): Unit = with(binding) {
        progressIndicator.hide()
        micButton.show()
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
        forwardButton.setOnClickListener { viewModel.goToNextSound() }
        backButton.setOnClickListener { viewModel.goToPreviousSound() }
        finishButton.setOnClickListener { popBackStack() }
    }
}
