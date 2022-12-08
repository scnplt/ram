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
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.tryGetWithResult
import dev.sertan.android.ram.core.common.tryMapWithResult
import dev.sertan.android.ram.core.common.tryWithLogger
import dev.sertan.android.ram.core.data.util.getStringAsStream
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

private const val VOICE_SUPPORT_SHARED_PREF_KEY = "user-settings"

internal class RamUserSettingsRepository @Inject constructor(
    private val sharedPref: SharedPreferences,
    private val logger: RamLogger
) : UserSettingsRepository {

    override fun getVoiceSupportStateStream(): Flow<Result<Boolean?>> = sharedPref
        .getStringAsStream(VOICE_SUPPORT_SHARED_PREF_KEY)
        .tryMapWithResult(logger) { it?.toBooleanStrictOrNull() }

    override suspend fun getVoiceSupportState(): Result<Boolean?> = tryGetWithResult(logger) {
        sharedPref.getString(VOICE_SUPPORT_SHARED_PREF_KEY, null)?.toBooleanStrictOrNull()
    }

    override suspend fun setVoiceSupportState(isActive: Boolean): Unit = tryWithLogger(logger) {
        sharedPref.edit().putString(VOICE_SUPPORT_SHARED_PREF_KEY, isActive.toString()).apply()
    }
}
