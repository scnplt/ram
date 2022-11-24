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

    suspend fun getMaterialsFromLocal(): Result<List<MaterialDto>>

    suspend fun getMaterialsFromRemote(): Result<List<MaterialDto>>

    suspend fun getMaterialFromLocalByUid(uid: String): Result<MaterialDto?>

    suspend fun getMaterialFromRemoteByUid(uid: String): Result<MaterialDto?>

    suspend fun saveMaterialToLocal(vararg materialArray: MaterialDto): Result<Unit>

    suspend fun deleteMaterialFromLocal(vararg materialArray: MaterialDto): Result<Unit>

    suspend fun updateLocalMaterial(material: MaterialDto): Result<Unit>
}
