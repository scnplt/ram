/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredomain.repository

import dev.sertan.android.ram.coredomain.model.MaterialDto

interface MaterialRepository {

    suspend fun getAllMaterials(): Result<List<MaterialDto>>

    suspend fun getMaterialById(materialId: Long): Result<MaterialDto?>

    suspend fun getQuestionMaterials(questionId: Long): Result<List<MaterialDto>>

    suspend fun saveMaterial(vararg materialDtoArray: MaterialDto): Result<Unit>

    suspend fun deleteMaterial(vararg materialDtoArray: MaterialDto): Result<Unit>

    suspend fun updateMaterial(materialDto: MaterialDto): Result<Unit>
}
