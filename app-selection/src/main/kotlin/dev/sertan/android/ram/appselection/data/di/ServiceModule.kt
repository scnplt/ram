/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.data.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.appselection.data.service.BaseService
import dev.sertan.android.ram.appselection.data.service.FirebaseService
import dev.sertan.android.ram.appselection.data.service.model.NetworkMaterial
import dev.sertan.android.ram.appselection.data.service.model.NetworkQuestion
import dev.sertan.android.ram.core.common.getAppModuleName
import javax.inject.Singleton

private enum class CollectionType { MATERIAL, QUESTION }

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideMaterialService(
        @ApplicationContext context: Context,
        firestore: FirebaseFirestore
    ): BaseService<NetworkMaterial> = FirebaseService(
        collection = firestore.collection(getCollectionName(context, CollectionType.MATERIAL)),
        dataClass = NetworkMaterial::class.java
    )

    @Provides
    @Singleton
    fun provideQuestionService(
        @ApplicationContext context: Context,
        firestore: FirebaseFirestore
    ): BaseService<NetworkQuestion> = FirebaseService(
        collection = firestore.collection(getCollectionName(context, CollectionType.QUESTION)),
        dataClass = NetworkQuestion::class.java
    )

    private fun getCollectionName(context: Context, type: CollectionType): String =
        "${context.getAppModuleName()}-${type.name.lowercase()}"
}
