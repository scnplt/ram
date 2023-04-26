/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dev.sertan.android.ram.core.domain.usecase.TextToSpeechUseCase
import dev.sertan.android.ram.core.ui.TextToSpeechProvider
import dev.sertan.android.ram.core.ui.util.extension.navigateToOssLicenses
import dev.sertan.android.ram.core.ui.util.extension.repeatOnLifecycleStarted
import dev.sertan.android.ram.core.ui.util.extension.viewBinding
import dev.sertan.android.ram.feature.home.adapter.HomeListAdapter
import dev.sertan.android.ram.feature.home.adapter.HomeListItem
import dev.sertan.android.ram.feature.home.databinding.FragmentHomeBinding
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseHomeFragment : Fragment(R.layout.fragment_home), TextToSpeechProvider {

    abstract val items: List<HomeListItem>

    @Inject
    lateinit var adapter: HomeListAdapter

    @Inject
    lateinit var textToSpeechUseCase: TextToSpeechUseCase

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val onLifecycleStarted: suspend CoroutineScope.() -> Unit = {
        textToSpeechUseCase.getVoiceSupportStateStream().collect(::onTextToSpeechStateChanged)
    }

    private var changeVoiceSupportJob: Job? = null

    override fun changeTextToSpeechState() {
        changeVoiceSupportJob?.cancel()
        changeVoiceSupportJob = lifecycleScope.launch { textToSpeechUseCase.change() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repeatOnLifecycleStarted(onLifecycleStarted)
        with(binding) {
            aboutButton.setOnClickListener { navigateToOssLicenses() }
            changeVoiceSupportButton.setOnClickListener { changeTextToSpeechState() }
            contentRecyclerView.adapter = adapter
            adapter.items = items
        }
    }

    override fun onTextToSpeechStateChanged(isActive: Boolean) {
        binding.changeVoiceSupportButton.isActivated = isActive
    }
}
