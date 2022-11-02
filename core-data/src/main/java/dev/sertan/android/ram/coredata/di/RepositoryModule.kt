package dev.sertan.android.ram.coredata.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.corecommon.di.LocalDataSource
import dev.sertan.android.ram.corecommon.di.RemoteDataSource
import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import dev.sertan.android.ram.corecommon.repository.UserSettingsRepository
import dev.sertan.android.ram.coredata.database.dao.MaterialDao
import dev.sertan.android.ram.coredata.repository.local.LocalMaterialRepository
import dev.sertan.android.ram.coredata.repository.local.LocalUserSettingsRepository
import dev.sertan.android.ram.coredata.repository.remote.RemoteMaterialRepository
import dev.sertan.android.ram.coredata.service.MaterialService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    @Singleton
    @LocalDataSource
    fun provideLocalMaterialRepository(materialDao: MaterialDao): MaterialRepository =
        LocalMaterialRepository(materialDao)

    @Provides
    @Singleton
    @RemoteDataSource
    fun provideRemoteMaterialRepository(materialService: MaterialService): MaterialRepository =
        RemoteMaterialRepository(materialService)

    @Provides
    @Singleton
    @LocalDataSource
    fun provideLocalUserSettingsRepository(
        sharedPreferences: SharedPreferences
    ): UserSettingsRepository = LocalUserSettingsRepository(sharedPreferences)
}
