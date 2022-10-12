/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.database.dao

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.sertan.android.ram.data.model.QuestionEntity
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@HiltAndroidTest
internal class QuestionDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var questionDao: QuestionDao

    @Before
    fun setUp(): Unit = hiltRule.inject()

    @Test
    fun getAll_questions_emptyList(): Unit = runBlocking {
        val questions = questionDao.getAll()
        assertThat(questions).isEmpty()
    }

    @Test
    fun insert_questions(): Unit = runBlocking {
        val testQuestions = getTestQuestions(size = 3)
        questionDao.insert(questionEntityArray = testQuestions)

        val questions = questionDao.getAll()
        assertThat(questions).isNotEmpty()
        assertThat(questions).containsAtLeastElementsIn(testQuestions)
    }

    @Test
    fun insert_question(): Unit = runBlocking {
        val testQuestion = getTestQuestions(size = 1).first()
        questionDao.insert(testQuestion)

        val questions = questionDao.getAll()
        assertThat(questions).isNotEmpty()
        assertThat(questions).hasSize(1)
        assertThat(questions).contains(testQuestion)
    }

    @Test
    fun delete_questions(): Unit = runBlocking {
        val testQuestions = getTestQuestions(size = 2)
        with(questionDao) {
            insert(questionEntityArray = testQuestions)
            delete(questionEntityArray = testQuestions)
            val questions = getAll()
            assertThat(questions).isEmpty()
        }
    }

    @Test
    fun delete_question(): Unit = runBlocking {
        val testQuestion = getTestQuestions(size = 1).first()
        with(questionDao) {
            insert(testQuestion)
            delete(testQuestion)
            val questions = getAll()
            assertThat(questions).isEmpty()
        }
    }

    @Test
    fun update_question(): Unit = runBlocking {
        val testQuestion = getTestQuestions(size = 1).first()
        val expectedQuestion = testQuestion.copy(content = "new")
        with(questionDao) {
            insert(testQuestion)
            update(questionEntity = expectedQuestion)
            val actualQuestion = getById(questionId = testQuestion.questionId)
            assertThat(actualQuestion).isEqualTo(expectedQuestion)
        }
    }

    private fun getTestQuestions(size: Int): Array<QuestionEntity> = (1..size).map {
        QuestionEntity(
            questionId = it.toLong(),
            content = it.toString(),
            0
        )
    }.toTypedArray()
}
