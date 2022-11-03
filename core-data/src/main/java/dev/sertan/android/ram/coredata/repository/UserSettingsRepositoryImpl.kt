/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.repository

import android.content.SharedPreferences
import dev.sertan.android.ram.corecommon.repository.UserSettingsRepository
import dev.sertan.android.ram.corecommon.util.getStringAsStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private const val USER_SETTINGS_SHARED_PREF_KEY = "user-settings"

@Singleton
internal class UserSettingsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserSettingsRepository {

    override suspend fun getVoiceSupportState(): Result<Boolean?> = runCatching {
        sharedPreferences.getString(USER_SETTINGS_SHARED_PREF_KEY, null)?.toBooleanStrictOrNull()
    }

    override fun getVoiceSupportStateAsStream(): Flow<Result<Boolean?>> = sharedPreferences
        .getStringAsStream(USER_SETTINGS_SHARED_PREF_KEY)
        .map { Result.success(it?.toBooleanStrictOrNull()) }
        .catch { exception -> Result.failure<Boolean>(exception) }

    override suspend fun setVoiceSupportState(isActive: Boolean): Result<Unit> = runCatching {
        sharedPreferences.edit()
            .putString(USER_SETTINGS_SHARED_PREF_KEY, isActive.toString()).apply()
    }
}
