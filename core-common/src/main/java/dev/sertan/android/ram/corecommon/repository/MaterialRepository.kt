/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.corecommon.repository

import dev.sertan.android.ram.corecommon.model.MaterialDto

interface MaterialRepository {

    suspend fun getAllMaterials(): Result<List<MaterialDto>>

    suspend fun getMaterialByUid(materialUid: String): Result<MaterialDto?>

    suspend fun saveMaterial(vararg materialArray: MaterialDto): Result<Unit>

    suspend fun deleteMaterial(vararg materialArray: MaterialDto): Result<Unit>

    suspend fun updateMaterial(material: MaterialDto): Result<Unit>
}
