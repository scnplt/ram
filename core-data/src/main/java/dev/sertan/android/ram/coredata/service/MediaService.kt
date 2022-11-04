/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.service

import android.net.Uri
import com.google.firebase.storage.StorageReference
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
internal class MediaService @Inject constructor(private val storageRef: StorageReference) {

    suspend fun getMediaDownloadUri(mediaPath: String?): Uri? =
        mediaPath?.let { storageRef.child(it).downloadUrl.await() }
}