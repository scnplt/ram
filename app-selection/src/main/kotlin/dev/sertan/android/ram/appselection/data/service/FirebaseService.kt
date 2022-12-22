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
import kotlinx.coroutines.tasks.await

internal class FirebaseService<T> constructor(
    private val collection: CollectionReference,
    private val dataClass: Class<T>
) : BaseService<T> {

    override suspend fun getAll(): List<T> = collection.get().await().toObjects(dataClass)
}
