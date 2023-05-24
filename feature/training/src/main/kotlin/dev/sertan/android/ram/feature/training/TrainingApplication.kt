/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training

import dev.sertan.android.ram.core.ui.RamApplication
import dev.sertan.android.ram.feature.material.domain.usecase.GetMaterialsUseCase
import dev.sertan.android.ram.feature.training.domain.usecase.GetQuestionsUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class TrainingApplication : RamApplication() {

    @Inject
    lateinit var getMaterialsUseCase: GetMaterialsUseCase

    @Inject
    lateinit var getQuestionsUseCase: GetQuestionsUseCase

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            getMaterialsUseCase()
            getQuestionsUseCase()
        }
    }
}
