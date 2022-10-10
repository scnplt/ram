/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import dev.sertan.android.ram.data.model.AnswerEntity
import dev.sertan.android.ram.data.model.LessonEntity
import dev.sertan.android.ram.data.model.MaterialEntity
import dev.sertan.android.ram.data.model.QuestionEntity
import dev.sertan.android.ram.data.model.QuestionOptionEntity

@Dao
internal interface LessonDao {

    @Query("SELECT * FROM lessons")
    suspend fun getAllLessons(): List<LessonEntity>

    @Query("SELECT * FROM questions WHERE :lessonId == lessonId")
    suspend fun getLessonQuestions(lessonId: Int): List<QuestionEntity>

    @Query("SELECT * FROM question_options WHERE :questionId == questionId")
    suspend fun getQuestionOptions(questionId: Int): List<QuestionOptionEntity>

    @Query("SELECT * FROM materials WHERE :materialId == id")
    suspend fun getMaterial(materialId: Int): MaterialEntity?

    @Query("SELECT * FROM answers WHERE :questionId == questionId")
    suspend fun getAnswer(questionId: Int): AnswerEntity?
}
