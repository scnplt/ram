/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dev.sertan.android.ram.core.ui.databinding.FragmentProjectInfoBinding
import dev.sertan.android.ram.core.ui.util.extension.viewBinding
import kotlinx.coroutines.delay

class ProjectInfoFragment : Fragment(R.layout.fragment_project_info) {
    private val binding by viewBinding(FragmentProjectInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            delay(DEFAULT_HALF_DURATION_MS)
            nextState()
            delay(DEFAULT_HALF_DURATION_MS)
            requireActivity().finish()
        }
    }

    private fun nextState(): Unit = with(binding) {
        firstLineTextView.text = getText(R.string.this_publication_has_been_produced)
        secondLineTextView.text = getText(R.string.publication_date_mm_yyyy)
    }

    companion object {
        private const val DEFAULT_HALF_DURATION_MS = 3000L
    }
}
