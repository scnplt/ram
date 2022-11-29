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
import dev.sertan.android.ram.appcolor.data.service.model.NetworkMaterial
import dev.sertan.android.ram.appcolor.di.MaterialCollection
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
internal class MaterialService @Inject constructor(
    @MaterialCollection private val collectionRef: CollectionReference
) {

    suspend fun getAllMaterials(): List<NetworkMaterial> =
        collectionRef.get().await().toObjects(NetworkMaterial::class.java)

    suspend fun getMaterialByUid(materialId: String): NetworkMaterial? =
        collectionRef.document(materialId).get().await().toObject(NetworkMaterial::class.java)
}
