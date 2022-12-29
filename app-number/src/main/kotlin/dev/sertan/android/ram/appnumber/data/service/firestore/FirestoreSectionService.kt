/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.data.service.firestore

import com.google.firebase.firestore.CollectionReference
import dev.sertan.android.ram.appnumber.data.service.SectionService
import dev.sertan.android.ram.appnumber.data.service.model.NetworkSection
import dev.sertan.android.ram.core.data.di.Collection
import dev.sertan.android.ram.core.data.di.CollectionType.SECTION
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

internal class FirestoreSectionService @Inject constructor(
    @Collection(SECTION) private val sectionCollection: CollectionReference
) : SectionService {

    override suspend fun getSections(): List<NetworkSection> =
        sectionCollection.get().await().toObjects(NetworkSection::class.java)
}
