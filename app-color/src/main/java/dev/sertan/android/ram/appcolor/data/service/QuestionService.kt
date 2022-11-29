/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.data.service

import com.google.firebase.firestore.CollectionReference
import dev.sertan.android.ram.appcolor.data.service.model.NetworkQuestion
import dev.sertan.android.ram.appcolor.di.QuestionCollection
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
internal class QuestionService @Inject constructor(
    @QuestionCollection private val collectionRef: CollectionReference
) {

    suspend fun getAllQuestions(): List<NetworkQuestion> =
        collectionRef.get().await().toObjects(NetworkQuestion::class.java)
}
