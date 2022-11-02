/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.service

import com.google.firebase.firestore.CollectionReference
import dev.sertan.android.ram.coredata.di.MaterialCollection
import dev.sertan.android.ram.coredata.service.model.NetworkMaterial
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
internal class MaterialService @Inject constructor(
    @MaterialCollection private val materialCollection: CollectionReference
) {

    suspend fun getAllMaterials(): List<NetworkMaterial> =
        materialCollection.get().await().toObjects(NetworkMaterial::class.java)

    suspend fun getMaterialById(materialId: String): NetworkMaterial? =
        materialCollection.document(materialId).get().await().toObject(NetworkMaterial::class.java)

    suspend fun saveMaterial(vararg materialArray: NetworkMaterial) {
        materialArray.forEach { materialCollection.document(it.materialUid).set(it).await() }
    }

    suspend fun deleteMaterial(vararg materialArray: NetworkMaterial) {
        materialArray.forEach { materialCollection.document(it.materialUid).delete().await() }
    }

    suspend fun updateMaterial(material: NetworkMaterial) {
        materialCollection.document(material.materialUid).set(material).await()
    }
}
