/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.domain.usecase

import dev.sertan.android.ram.core.data.service.remoteconfig.RemoteConfigService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetApplicationConfigsUseCase @Inject constructor(
    private val remoteConfigService: RemoteConfigService
) {

    init {
        remoteConfigService.updateConfigs()
    }

    operator fun invoke(key: String): String = remoteConfigService.getConfig(key)
}
