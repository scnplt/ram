/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.service.remoteconfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dev.sertan.android.ram.core.common.log.RamLogger
import javax.inject.Inject

internal class FirebaseRemoteConfigService @Inject constructor(
    private val config: FirebaseRemoteConfig,
    private val logger: RamLogger
) : RemoteConfigService {

    override fun getConfig(key: String): String = config.getString(key)

    override fun updateConfigs() {
        config.fetchAndActivate().addOnCompleteListener { task ->
            logger.d("FirebaseRemoteConfigService: configs update - isSuccess ${task.isSuccessful}")
        }
    }
}
