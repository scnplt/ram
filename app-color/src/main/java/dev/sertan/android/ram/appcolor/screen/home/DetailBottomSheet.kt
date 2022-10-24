/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.home

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcolor.R
import dev.sertan.android.ram.appcolor.databinding.BottomSheetDetailBinding
import dev.sertan.android.ram.appcolor.screen.home.DetailBottomSheetDirections.Companion.actionDetailBottomSheetToQuestionFragment
import dev.sertan.android.ram.appcolor.screen.home.DetailBottomSheetDirections.Companion.actionDetailBottomSheetToTrainingFragment
import dev.sertan.android.ram.coreui.ui.BaseBottomSheetFragment
import dev.sertan.android.ram.coreui.util.navigateTo
import dev.sertan.android.ram.coreui.util.speak
import dev.sertan.android.ram.coreui.util.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
internal class DetailBottomSheet : BaseBottomSheetFragment(R.layout.bottom_sheet_detail) {

    @Inject
    lateinit var textToSpeech: TextToSpeech

    private val binding by viewBinding(BottomSheetDetailBinding::bind)
    private val args by navArgs<DetailBottomSheetArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
    }

    override fun onStart() {
        super.onStart()
        textToSpeech.speak(binding.contentTextView.text)
    }

    override fun onPause() {
        super.onPause()
        textToSpeech.stop()
    }

    private fun setUpComponents(): Unit = with(binding) {
        contentTextView.text = resources.getQuantityString(
            R.plurals.duration_value,
            args.duration,
            args.duration
        )
        questionButton.isVisible = args.isQuestionButtonActive
        questionButton.setOnClickListener {
            navigateTo(actionDetailBottomSheetToQuestionFragment())
        }
        trainingButton.setOnClickListener {
            navigateTo(actionDetailBottomSheetToTrainingFragment())
        }
    }
}
