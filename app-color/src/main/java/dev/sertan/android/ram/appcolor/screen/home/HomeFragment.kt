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
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcolor.R
import dev.sertan.android.ram.appcolor.databinding.FragmentHomeBinding
import dev.sertan.android.ram.coreui.util.showToast
import dev.sertan.android.ram.coreui.util.viewBinding

@AndroidEntryPoint
internal class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
    }

    private fun setUpComponents(): Unit = with(binding) {
        helpButton.setOnClickListener { showToast("Not yet implemented") }
        changeVoiceSupportButton.setOnClickListener { showToast("Not yet implemented") }
        trainingButton.setOnClickListener { showToast("Not yet implemented") }
        practiceButton.setOnClickListener { showToast("Not yet implemented") }
        sendFeedbackTextView.setOnClickListener { showToast("Not yet implemented") }
    }
}
