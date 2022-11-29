/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.repository

import android.content.SharedPreferences
import dev.sertan.android.ram.core.domain.UserSettingsRepository
import dev.sertan.android.ram.core.util.getStringAsStream
import dev.sertan.android.ram.core.util.tryGetResultWithLog
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

private const val USER_SETTINGS_SHARED_PREF_KEY = "user-settings"

@Singleton
internal class UserSettingsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserSettingsRepository {

    override suspend fun getVoiceSupportState(): Result<Boolean?> = tryGetResultWithLog {
        sharedPreferences.getString(USER_SETTINGS_SHARED_PREF_KEY, null)?.toBooleanStrictOrNull()
    }

    override fun getVoiceSupportStateAsStream(): Flow<Result<Boolean?>> = sharedPreferences
        .getStringAsStream(USER_SETTINGS_SHARED_PREF_KEY)
        .tryGetResultWithLog { it?.toBooleanStrictOrNull() }

    override suspend fun setVoiceSupportState(isActive: Boolean): Result<Unit> =
        tryGetResultWithLog {
            sharedPreferences.edit()
                .putString(USER_SETTINGS_SHARED_PREF_KEY, isActive.toString()).apply()
        }
}
