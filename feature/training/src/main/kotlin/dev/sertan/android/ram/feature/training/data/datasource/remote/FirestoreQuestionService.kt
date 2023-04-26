/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training.data.datasource.remote

import com.google.firebase.firestore.CollectionReference
import dev.sertan.android.ram.core.data.di.Collection
import dev.sertan.android.ram.core.data.di.CollectionType
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

internal class FirestoreQuestionService @Inject constructor(
    @Collection(CollectionType.QUESTION) private val collection: CollectionReference
) {

    suspend fun getQuestions(): List<NetworkQuestion> =
        collection.get().await().toObjects(NetworkQuestion::class.java)
}
