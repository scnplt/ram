/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appletter.ui.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dev.sertan.android.ram.appletter.R
import dev.sertan.android.ram.appletter.databinding.FragmentResultBinding
import dev.sertan.android.ram.appletter.ui.result.ResultFragmentDirections.Companion.actionResultFragmentToPracticeFragment
import dev.sertan.android.ram.appletter.ui.result.ResultFragmentDirections.Companion.actionResultFragmentToWriteFragment
import dev.sertan.android.ram.core.ui.util.navTo
import dev.sertan.android.ram.core.ui.util.popBackStack
import dev.sertan.android.ram.core.ui.util.setRatingByPercent
import dev.sertan.android.ram.core.ui.util.viewBinding

internal class ResultFragment : Fragment(R.layout.fragment_result) {
    private val binding by viewBinding(FragmentResultBinding::bind)
    private val args by navArgs<ResultFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            scoreTextView.text =
                getString(dev.sertan.android.ram.core.ui.R.string.score_percent, args.score)
            ratingBar.setRatingByPercent(args.score)
            restartButton.setOnClickListener { navTo(actionResultFragmentToPracticeFragment()) }
            finishButton.setOnClickListener { popBackStack() }
            writeButton.setOnClickListener { navTo(actionResultFragmentToWriteFragment()) }
        }
    }
}
