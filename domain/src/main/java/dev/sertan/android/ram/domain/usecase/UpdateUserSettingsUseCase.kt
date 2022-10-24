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
import dev.sertan.android.ram.domain.mapper.toDomainModel
import javax.inject.Inject

class UpdateUserSettingsUseCase @Inject constructor(
    @LocalDataSource private val userConfigsRepository: UserConfigsRepository
) {

    suspend operator fun invoke(userConfigs: UserConfigs) {
        userConfigsRepository.setUserConfigs(userConfigsDto = userConfigs.toDomainModel())
    }
}
