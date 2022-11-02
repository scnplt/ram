/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredomain.usecase

import dev.sertan.android.ram.corecommon.di.LocalDataSource
import dev.sertan.android.ram.corecommon.di.RemoteDataSource
import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import javax.inject.Inject

class UpdateLocalMaterialsUseCase @Inject constructor(
    @LocalDataSource private val localMaterialRepository: MaterialRepository,
    @RemoteDataSource private val remoteMaterialRepository: MaterialRepository
) {

    suspend operator fun invoke(): Boolean {
        val remoteResult = remoteMaterialRepository.getAllMaterials()
        if (remoteResult.isFailure) return false

        return remoteResult.getOrNull()?.let {
            localMaterialRepository.saveMaterial(materialArray = it.toTypedArray()).isSuccess
        } ?: true
    }
}
