/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.question.data.datasource.remote

import com.google.firebase.firestore.CollectionReference
import dev.sertan.android.ram.core.data.di.Collection
import dev.sertan.android.ram.core.data.di.CollectionType.MATERIAL
import dev.sertan.android.ram.feature.question.data.datasource.remote.model.NetworkMaterial
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

internal class FirestoreMaterialService @Inject constructor(
    @Collection(MATERIAL) private val collection: CollectionReference
) {

    suspend fun getMaterials(): List<NetworkMaterial> =
        collection.get().await().toObjects(NetworkMaterial::class.java)
}
