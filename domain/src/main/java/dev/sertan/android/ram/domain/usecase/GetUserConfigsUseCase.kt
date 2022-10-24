/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.domain.usecase

import dev.sertan.android.ram.corecommon.di.LocalDataSource
import dev.sertan.android.ram.corecommon.repository.UserConfigsRepository
import dev.sertan.android.ram.coreui.model.UserConfigs
import dev.sertan.android.ram.domain.mapper.toUiModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserConfigsUseCase @Inject constructor(
    @LocalDataSource private val userConfigsRepository: UserConfigsRepository,
    private val createUserConfigsUseCase: CreateUserConfigsUseCase
) {

    operator fun invoke(): Flow<Result<UserConfigs>> {
        return userConfigsRepository.getUserConfigsAsStream().map { result ->
            result.map { userConfigsDto ->
                userConfigsDto?.toUiModel() ?: UserConfigs().also { createUserConfigsUseCase(it) }
            }
        }
    }
}
