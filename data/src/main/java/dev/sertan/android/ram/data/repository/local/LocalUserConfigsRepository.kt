/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.repository.local

import dev.sertan.android.ram.corecommon.model.UserConfigsDto
import dev.sertan.android.ram.corecommon.repository.UserConfigsRepository
import dev.sertan.android.ram.data.util.SharedPrefUtility
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

internal class LocalUserConfigsRepository(
    private val sharedPrefUtility: SharedPrefUtility<UserConfigsDto>
) : UserConfigsRepository {

    override suspend fun getUserConfigs(): Result<UserConfigsDto?> = runCatching {
        sharedPrefUtility.getData()
    }

    override suspend fun getUserConfigsAsStream(): Flow<Result<UserConfigsDto?>> {
        return sharedPrefUtility.getDataAsStream().map { Result.success(it) }
            .catch { exception -> Result.failure<UserConfigsDto?>(exception) }
    }

    override suspend fun updateUserConfigs(userConfigsDto: UserConfigsDto): Result<Unit> {
        return runCatching { sharedPrefUtility.putData(data = userConfigsDto) }
    }
}
