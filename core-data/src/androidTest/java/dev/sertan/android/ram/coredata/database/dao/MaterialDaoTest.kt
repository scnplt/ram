/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.database.dao

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.sertan.android.ram.coredata.model.MaterialEntity
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@HiltAndroidTest
internal class MaterialDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var materialDao: MaterialDao

    @Before
    fun setUp(): Unit = hiltRule.inject()

    @Test
    fun getAll_materials_emptyList(): Unit = runBlocking {
        val materials = materialDao.getAll()
        assertThat(materials).isEmpty()
    }

    @Test
    fun insert_materials(): Unit = runBlocking {
        val testMaterials = getTestMaterials(size = 3)
        materialDao.insert(materialEntityArray = testMaterials)

        val materials = materialDao.getAll()
        assertThat(materials).isNotEmpty()
        assertThat(materials).containsAtLeastElementsIn(testMaterials)
    }

    @Test
    fun insert_material(): Unit = runBlocking {
        val testMaterial = getTestMaterials(size = 1).first()
        materialDao.insert(testMaterial)

        val materials = materialDao.getAll()
        assertThat(materials).isNotEmpty()
        assertThat(materials).hasSize(1)
        assertThat(materials).contains(testMaterial)
    }

    @Test
    fun delete_materials(): Unit = runBlocking {
        val testMaterials = getTestMaterials(size = 2)
        with(materialDao) {
            insert(materialEntityArray = testMaterials)
            delete(materialEntityArray = testMaterials)
            val materials = getAll()
            assertThat(materials).isEmpty()
        }
    }

    @Test
    fun delete_material(): Unit = runBlocking {
        val testMaterial = getTestMaterials(size = 1).first()
        with(materialDao) {
            insert(testMaterial)
            delete(testMaterial)
            val materials = getAll()
            assertThat(materials).isEmpty()
        }
    }

    @Test
    fun update_material(): Unit = runBlocking {
        val testMaterial = getTestMaterials(size = 1).first()
        val expectedMaterial = testMaterial.copy(description = "new")
        with(materialDao) {
            insert(testMaterial)
            update(materialEntity = expectedMaterial)
            val actualMaterial = getById(materialId = testMaterial.materialId)
            assertThat(actualMaterial).isEqualTo(expectedMaterial)
        }
    }

    private fun getTestMaterials(size: Int): Array<MaterialEntity> = (1..size).map {
        MaterialEntity(
            materialId = it.toLong(),
            description = it.toString(),
            mediaUrl = "",
            attribution = null
        )
    }.toTypedArray()
}
