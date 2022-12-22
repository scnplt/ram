/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.data.service

import com.google.firebase.firestore.CollectionReference
import dev.sertan.android.ram.appselection.data.di.Collection
import dev.sertan.android.ram.appselection.data.di.CollectionType.MATERIAL
import dev.sertan.android.ram.appselection.data.di.CollectionType.QUESTION
import dev.sertan.android.ram.appselection.data.service.model.NetworkMaterial
import dev.sertan.android.ram.appselection.data.service.model.NetworkQuestion
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

internal class FirestoreSelectionService @Inject constructor(
    @Collection(MATERIAL) private val materialCollection: CollectionReference,
    @Collection(QUESTION) private val questionCollection: CollectionReference
) : SelectionService {

    override suspend fun getMaterials(): List<NetworkMaterial> =
        materialCollection.get().await().toObjects(NetworkMaterial::class.java)

    override suspend fun getQuestions(): List<NetworkQuestion> =
        questionCollection.get().await().toObjects(NetworkQuestion::class.java)
}
