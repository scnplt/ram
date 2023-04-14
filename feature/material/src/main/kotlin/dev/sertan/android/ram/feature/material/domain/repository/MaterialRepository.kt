/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.material.domain.repository

import dev.sertan.android.ram.feature.material.domain.model.MaterialDto

internal interface MaterialRepository {

    suspend fun getMaterials(update: Boolean = false): Result<List<MaterialDto>>

    suspend fun getMaterial(materialUid: String): Result<MaterialDto?>

    suspend fun saveMaterialToLocal(material: MaterialDto): Boolean

    suspend fun refreshMaterials(): Boolean

    suspend fun updateMaterialsFromRemote()
}
