/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.data.datasource.remote.service

import com.google.firebase.firestore.CollectionReference
import dev.sertan.android.ram.appmemory.data.datasource.remote.model.NetworkMatchingQuestion
import dev.sertan.android.ram.core.data.di.Collection
import dev.sertan.android.ram.core.data.di.CollectionType
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

internal class FirestoreMatchingQuestionService @Inject constructor(
    @Collection(CollectionType.MATCHING) private val collection: CollectionReference
) {

    suspend fun getQuestions(): List<NetworkMatchingQuestion> =
        collection.get().await().toObjects(NetworkMatchingQuestion::class.java)
}
