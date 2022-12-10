/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.domain.usecase

import dev.sertan.android.ram.core.common.Dispatcher
import dev.sertan.android.ram.core.common.RamDispatcher
import dev.sertan.android.ram.core.data.repository.MaterialRepository
import dev.sertan.android.ram.core.data.repository.QuestionRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@Singleton
class RefreshLocalDataUseCase @Inject constructor(
    private val materialRepository: MaterialRepository,
    private val questionRepository: QuestionRepository,
    @Dispatcher(RamDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) {

    init {
        CoroutineScope(ioDispatcher).launch { invoke() }
    }

    suspend operator fun invoke(): Unit = supervisorScope {
        launch { materialRepository.refreshMaterials() }
        questionRepository.refreshQuestions()
    }
}
