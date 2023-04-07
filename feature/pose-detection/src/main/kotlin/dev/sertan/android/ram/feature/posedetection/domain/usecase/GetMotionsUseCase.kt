/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.posedetection.domain.usecase

import dev.sertan.android.ram.feature.posedetection.domain.motion.LookDownMotion
import dev.sertan.android.ram.feature.posedetection.domain.motion.LookLeftMotion
import dev.sertan.android.ram.feature.posedetection.domain.motion.LookRightMotion
import dev.sertan.android.ram.feature.posedetection.domain.motion.Motion
import dev.sertan.android.ram.feature.posedetection.domain.motion.RaiseLeftHandMotion
import dev.sertan.android.ram.feature.posedetection.domain.motion.RaiseRightHandMotion
import javax.inject.Inject

class GetMotionsUseCase @Inject constructor() {

    operator fun invoke(): List<Motion> = listOf(
        RaiseLeftHandMotion(),
        RaiseRightHandMotion(),
        LookLeftMotion(),
        LookRightMotion(),
        LookDownMotion()
    )
}
