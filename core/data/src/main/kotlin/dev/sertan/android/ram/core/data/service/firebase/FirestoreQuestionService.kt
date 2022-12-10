/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.service.firebase

import com.google.firebase.firestore.CollectionReference
import dev.sertan.android.ram.core.data.di.CollectionType
import dev.sertan.android.ram.core.data.di.FirebaseCollection
import dev.sertan.android.ram.core.data.service.QuestionService
import dev.sertan.android.ram.core.data.service.model.NetworkQuestion
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

internal class FirestoreQuestionService @Inject constructor(
    @FirebaseCollection(CollectionType.QUESTION) private val collectionRef: CollectionReference
) : QuestionService {

    override suspend fun getQuestions(): List<NetworkQuestion> =
        collectionRef.get().await().toObjects(NetworkQuestion::class.java)
}
