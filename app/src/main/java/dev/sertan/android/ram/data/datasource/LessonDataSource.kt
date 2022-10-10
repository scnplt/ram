/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.datasource

import dev.sertan.android.ram.data.model.LessonEntity
import dev.sertan.android.ram.data.model.MaterialEntity
import dev.sertan.android.ram.data.model.QuestionEntity

internal interface LessonDataSource {
    suspend fun getLessons(): List<LessonEntity>
    suspend fun createLesson(vararg lessonEntity: LessonEntity)
    suspend fun deleteLesson(vararg lessonEntity: LessonEntity)
}
