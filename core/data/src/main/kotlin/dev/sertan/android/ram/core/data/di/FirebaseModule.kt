/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.di

import android.content.Context
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.core.common.BuildConfig
import dev.sertan.android.ram.core.common.getAppModuleName
import dev.sertan.android.ram.core.data.service.remoteconfig.FirebaseRemoteConfigService
import dev.sertan.android.ram.core.data.service.remoteconfig.RemoteConfigService
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Collection(val type: CollectionType)

enum class CollectionType { MATERIAL, QUESTION, SECTION }

internal fun CollectionType.getReferenceName(context: Context): String =
    "${BuildConfig.BUILD_TYPE}/${context.getAppModuleName()}/${name.lowercase()}"

private const val CONFIG_MIN_FETCH_INTERVAL_SEC = 3600L

@Module
@InstallIn(SingletonComponent::class)
internal class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(
            remoteConfigSettings { minimumFetchIntervalInSeconds = CONFIG_MIN_FETCH_INTERVAL_SEC }
        )
    }

    @Provides
    @Singleton
    @Collection(CollectionType.MATERIAL)
    fun provideMaterialCollectionReference(
        @ApplicationContext context: Context,
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection(CollectionType.MATERIAL.getReferenceName(context))

    @Provides
    @Singleton
    @Collection(CollectionType.QUESTION)
    fun provideQuestionCollectionReference(
        @ApplicationContext context: Context,
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection(CollectionType.QUESTION.getReferenceName(context))

    @Provides
    @Singleton
    @Collection(CollectionType.SECTION)
    fun provideSectionCollectionReference(
        @ApplicationContext context: Context,
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection(CollectionType.SECTION.getReferenceName(context))

    @Provides
    @Singleton
    fun providerRemoteConfigService(
        service: FirebaseRemoteConfigService
    ): RemoteConfigService = service
}
