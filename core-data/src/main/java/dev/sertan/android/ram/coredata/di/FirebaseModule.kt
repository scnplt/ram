/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.coredata.BuildConfig
import javax.inject.Qualifier
import javax.inject.Singleton

private const val MATERIAL_COLLECTION_REF_NAME = "materials"

@Module
@InstallIn(SingletonComponent::class)
internal object FirebaseModule {

    @Provides
    @Singleton
    @MaterialCollection
    fun provideMaterialCollectionReference(): CollectionReference =
        Firebase.firestore.collection(MATERIAL_COLLECTION_REF_NAME)

    @Provides
    @Singleton
    fun provideStorageReference(): StorageReference =
        Firebase.storage(BuildConfig.FIREBASE_STORAGE_BUCKET).reference
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MaterialCollection
