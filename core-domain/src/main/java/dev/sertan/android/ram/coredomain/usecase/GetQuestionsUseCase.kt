/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredomain.usecase

import dev.sertan.android.ram.corecommon.repository.QuestionRepository
import dev.sertan.android.ram.coredomain.mapper.toUiModel
import dev.sertan.android.ram.coreui.model.Question
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class GetQuestionsUseCase @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val updateLocalQuestionsUseCase: UpdateLocalQuestionsUseCase
) {

    suspend operator fun invoke(): List<Question> = withContext(Dispatchers.IO) {
        questionRepository.run {
            getQuestionsFromLocal().getOrNull()
                ?: getQuestionsFromRemote().getOrNull().also { updateLocalQuestionsUseCase() }
        }?.map {
            val materials = it.materialUidList
                .mapNotNull { materialUid -> getMaterialsUseCase.getByUid(uid = materialUid) }
            it.toUiModel(materials)
        }.orEmpty()
    }
}
