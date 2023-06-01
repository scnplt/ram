/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training

import android.os.Bundle
import android.view.View
import androidx.navigation.NavDirections
import dev.sertan.android.ram.core.ui.util.extension.navTo
import dev.sertan.android.ram.core.ui.util.extension.savedStateHandeListener
import dev.sertan.android.ram.feature.home.BaseHomeFragment
import dev.sertan.android.ram.feature.material.domain.usecase.GetMaterialsUseCase
import dev.sertan.android.ram.feature.training.domain.usecase.GetQuestionsUseCase
import dev.sertan.android.ram.feature.training.ui.training.TrainingFragment
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class TrainingHomeFragment : BaseHomeFragment() {

    @Inject
    lateinit var getMaterialsUseCase: GetMaterialsUseCase

    @Inject
    lateinit var getQuestionsUseCase: GetQuestionsUseCase

    open var directionAfterFinished: NavDirections? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            getMaterialsUseCase()
            getQuestionsUseCase()
        }
        directionAfterFinished?.let { nextDirection ->
            savedStateHandeListener<Boolean>(TrainingFragment.KEY_FINISHED) { isFinished ->
                if (isFinished) navTo(nextDirection)
            }
        }
    }
}
