/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.datasource

import dev.sertan.android.ram.data.model.MaterialEntity

internal interface MaterialDataSource {
    suspend fun getLessonMaterials(lessonId: Long): List<MaterialEntity>
    suspend fun getQuestionMaterials(questionId: Long): List<MaterialEntity>
    suspend fun createMaterial(vararg materialEntity: MaterialEntity)
    suspend fun deleteMaterial(vararg materialEntity: MaterialEntity)
}
