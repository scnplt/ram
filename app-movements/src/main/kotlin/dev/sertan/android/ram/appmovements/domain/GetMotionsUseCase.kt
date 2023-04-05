/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.domain

import dev.sertan.android.ram.appmovements.domain.motion.LookLeftMotion
import dev.sertan.android.ram.appmovements.domain.motion.LookRightMotion
import dev.sertan.android.ram.appmovements.domain.motion.Motion
import dev.sertan.android.ram.appmovements.domain.motion.RaiseLeftHandMotion
import dev.sertan.android.ram.appmovements.domain.motion.RaiseRightHandMotion
import javax.inject.Inject

internal class GetMotionsUseCase @Inject constructor() {

    operator fun invoke(): List<Motion> = listOf(
        RaiseLeftHandMotion(),
        RaiseRightHandMotion(),
        LookLeftMotion(),
        LookRightMotion()
    )
}
