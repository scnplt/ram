/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreDataReceiver<T>(
    private val firestore: FirebaseFirestore,
    private val collectionName: String,
    private val dataClass: Class<T>
) {

    private val collection by lazy { firestore.collection(collectionName) }

    suspend fun getAll(): List<T> = collection.get().await().toObjects(dataClass)
}
