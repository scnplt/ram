/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredomain.usecase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sertan.android.ram.corecommon.repository.QuestionRepository
import dev.sertan.android.ram.coredomain.mapper.toUiModel
import dev.sertan.android.ram.coredomain.worker.UpdateLocalQuestionsWorker
import dev.sertan.android.ram.coreui.model.Question
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class GetQuestionsUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val questionRepository: QuestionRepository,
    private val getMaterialsUseCase: GetMaterialsUseCase
) {

    suspend operator fun invoke(): List<Question> = withContext(Dispatchers.IO) {
        questionRepository.getQuestionsFromLocal().getOrNull()?.shuffled()
            ?.map {
                val materials = it.materialUidList.mapNotNull { materialUid ->
                    getMaterialsUseCase.getByUid(uid = materialUid)
                }
                it.toUiModel(materials)
            }
            .takeUnless { it.isNullOrEmpty() }
            ?: run {
                UpdateLocalQuestionsWorker.uniqueStart(context)
                emptyList()
            }
    }
}
