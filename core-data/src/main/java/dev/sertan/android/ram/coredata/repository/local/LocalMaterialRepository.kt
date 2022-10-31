/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.repository.local

import dev.sertan.android.ram.corecommon.model.MaterialDto
import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import dev.sertan.android.ram.coredata.database.dao.MaterialDao
import dev.sertan.android.ram.coredata.database.dao.QuestionWithMaterialsDao
import dev.sertan.android.ram.coredata.model.mapper.toDataModel
import dev.sertan.android.ram.coredata.model.mapper.toDomainModel
import javax.inject.Inject

internal class LocalMaterialRepository @Inject constructor(
    private val materialDao: MaterialDao,
    private val questionWithMaterialsDao: QuestionWithMaterialsDao
) : MaterialRepository {

    override suspend fun getAllMaterials(): Result<List<MaterialDto>> = runCatching {
        materialDao.getAll().toDomainModel()
    }

    override suspend fun getMaterialById(materialId: Long): Result<MaterialDto?> = runCatching {
        materialDao.getById(materialId).toDomainModel()
    }

    override suspend fun getQuestionMaterials(questionId: Long): Result<List<MaterialDto>> {
        return runCatching {
            val questionMaterialIdList =
                questionWithMaterialsDao.getAllById(questionId).map { it.materialId }
            materialDao.getAllWithIds(materialIds = questionMaterialIdList).toDomainModel()
        }
    }

    override suspend fun saveMaterial(vararg materialDtoArray: MaterialDto): Result<Unit> {
        return runCatching {
            materialDao.insert(materialEntityArray = materialDtoArray.toDataModel())
        }
    }

    override suspend fun deleteMaterial(vararg materialDtoArray: MaterialDto): Result<Unit> {
        return runCatching {
            materialDao.delete(materialEntityArray = materialDtoArray.toDataModel())
        }
    }

    override suspend fun updateMaterial(materialDto: MaterialDto): Result<Unit> = runCatching {
        materialDao.update(materialEntity = materialDto.toDataModel())
    }
}
