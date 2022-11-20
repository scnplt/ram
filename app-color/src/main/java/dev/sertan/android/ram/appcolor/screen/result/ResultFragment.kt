/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dev.sertan.android.ram.appcolor.R
import dev.sertan.android.ram.appcolor.databinding.FragmentResultBinding
import dev.sertan.android.ram.appcolor.screen.result.ResultFragmentDirections.Companion.actionResultFragmentToHomeFragment
import dev.sertan.android.ram.appcolor.screen.result.ResultFragmentDirections.Companion.actionResultFragmentToPracticeFragment
import dev.sertan.android.ram.coreui.util.extension.navigateTo
import dev.sertan.android.ram.coreui.util.extension.setRatingByPercent
import dev.sertan.android.ram.coreui.util.extension.viewBinding

internal class ResultFragment : Fragment(R.layout.fragment_result) {
    private val binding by viewBinding(FragmentResultBinding::bind)
    private val args by navArgs<ResultFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            scoreTextView.text = getString(R.string.score_percent, args.score)
            ratingBar.setRatingByPercent(args.score)
            restartButton.setOnClickListener {
                navigateTo(actionResultFragmentToPracticeFragment())
            }
            finishButton.setOnClickListener { navigateTo(actionResultFragmentToHomeFragment()) }
        }
    }
}
