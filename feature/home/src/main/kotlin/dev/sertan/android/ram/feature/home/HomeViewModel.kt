/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.android.ram.core.domain.usecase.VoiceSupportUseCase
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val voiceSupportUseCase: VoiceSupportUseCase
) : ViewModel() {

    private var changeVoiceSupportJob: Job? = null

    val voiceSupportState: SharedFlow<Boolean> = voiceSupportUseCase.getVoiceSupportStateStream()
        .shareIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed())

    fun changeVoiceSupportState() {
        changeVoiceSupportJob?.cancel()
        changeVoiceSupportJob = viewModelScope.launch { voiceSupportUseCase.change() }
    }
}
