/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.feature.question.ui.QuestionFragment
import dev.sertan.android.ram.appselection.NavGraphDirections.Companion.actionGlobalResultFragment
import dev.sertan.android.ram.appselection.R
import dev.sertan.android.ram.appselection.databinding.FragmentHomeBinding
import dev.sertan.android.ram.appselection.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToAboutFragment
import dev.sertan.android.ram.appselection.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToDrawingFragment
import dev.sertan.android.ram.appselection.ui.home.HomeFragmentDirections.Companion.actionHomeFragmentToQuestionFragment
import dev.sertan.android.ram.appselection.ui.result.ResultFragment
import dev.sertan.android.ram.core.ui.RamActivity
import dev.sertan.android.ram.core.ui.util.extension.labelWithoutPrefix
import dev.sertan.android.ram.core.ui.util.extension.navigateTo
import dev.sertan.android.ram.core.ui.util.extension.viewBinding
import dev.sertan.android.ram.core.ui.util.getNavResult
import dev.sertan.android.ram.feature.training.ui.TrainingFragment
import kotlinx.coroutines.flow.FlowCollector

@AndroidEntryPoint
internal class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()

    private val voiceSupportStateFlowCollector = FlowCollector<Boolean> { isActive ->
        binding.changeVoiceSupportButton.isActivated = isActive
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNavResult<Boolean>(TrainingFragment.NAV_RESULT_FINISH) {
            if (it) navigateTo(actionHomeFragmentToQuestionFragment())
        }
        getNavResult<Float>(QuestionFragment.NAV_RESULT_SCORE) {
            navigateTo(actionGlobalResultFragment(score = it))
        }
        getNavResult<Boolean>(ResultFragment.NAVIGATE_TO_QUESTION) {
            if (it) navigateTo(actionHomeFragmentToQuestionFragment())
        }
        setUpComponents()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.voiceSupportState.collect(voiceSupportStateFlowCollector)
            }
        }
    }

    private fun setUpComponents(): Unit = with(binding) {
        changeVoiceSupportButton.setOnClickListener { viewModel.changeVoiceSupportState() }
        aboutButton.setOnClickListener { navigateTo(actionHomeFragmentToAboutFragment()) }
        drawingButton.setOnClickListener { navigateTo(actionHomeFragmentToDrawingFragment()) }
        (activity as? RamActivity)?.run {
            titleTextView.text = requireContext().labelWithoutPrefix
            trainingButton.setOnClickListener { navigateTo(trainingDirection) }
            practiceButton.setOnClickListener { navigateTo(practiceDirection) }
        }
    }
}
