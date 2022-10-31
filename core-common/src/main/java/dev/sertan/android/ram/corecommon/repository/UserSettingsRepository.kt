/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.corecommon.repository

import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {

    suspend fun getVoiceSupportState(): Result<Boolean?>

    fun getVoiceSupportStateAsStream(): Flow<Result<Boolean?>>

    suspend fun setVoiceSupportState(isActive: Boolean): Result<Unit>
}