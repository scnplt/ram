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
import android.content.SharedPreferences
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
import dev.sertan.android.ram.core.data.util.getSavedCollectionGroupName
import javax.inject.Qualifier
import javax.inject.Singleton

private const val CONFIG_MIN_FETCH_INTERVAL_SEC = 3600L

@Qualifier
@Retention
annotation class CollectionPath

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
    fun providerRemoteConfigService(
        service: FirebaseRemoteConfigService
    ): RemoteConfigService = service

    @Provides
    @Singleton
    @CollectionPath
    fun provideCollectionPath(
        @ApplicationContext context: Context,
        sharePref: SharedPreferences
    ): String {
        val groupName = sharePref.getSavedCollectionGroupName() ?: context.getAppModuleName()
        return "${BuildConfig.BUILD_TYPE}/$groupName"
    }
}
