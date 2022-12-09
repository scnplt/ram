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
import dev.sertan.android.ram.core.data.service.MaterialService
import dev.sertan.android.ram.core.data.service.model.NetworkMaterial
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

internal class FirestoreMaterialService @Inject constructor(
    @FirebaseCollection(CollectionType.MATERIAL) private val collectionRef: CollectionReference
) : MaterialService {

    override suspend fun getMaterials(): List<NetworkMaterial> =
        collectionRef.get().await().toObjects(NetworkMaterial::class.java)
}
