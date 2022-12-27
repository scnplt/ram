/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.feature.question.data.service

import com.google.firebase.firestore.CollectionReference
import dev.sertan.android.feature.question.data.service.model.NetworkQuestion
import dev.sertan.android.ram.core.data.di.Collection
import dev.sertan.android.ram.core.data.di.CollectionType.QUESTION
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

internal class FirestoreQuestionService @Inject constructor(
    @Collection(QUESTION) private val questionCollection: CollectionReference
) : QuestionService {

    override suspend fun getQuestions(): List<NetworkQuestion> =
        questionCollection.get().await().toObjects(NetworkQuestion::class.java)
}
