package dev.sertan.android.ram.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.corecommon.di.LocalDataSource
import dev.sertan.android.ram.corecommon.model.UserConfigsDto
import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import dev.sertan.android.ram.corecommon.repository.UserConfigsRepository
import dev.sertan.android.ram.data.database.dao.MaterialDao
import dev.sertan.android.ram.data.database.dao.QuestionWithMaterialsDao
import dev.sertan.android.ram.data.repository.local.LocalMaterialRepository
import dev.sertan.android.ram.data.repository.local.LocalUserConfigsRepository
import dev.sertan.android.ram.data.util.SharedPrefUtility
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    @Singleton
    @LocalDataSource
    fun provideLocalMaterialRepository(
        materialDao: MaterialDao,
        questionWithMaterialsDao: QuestionWithMaterialsDao
    ): MaterialRepository = LocalMaterialRepository(materialDao, questionWithMaterialsDao)

    @Provides
    @Singleton
    @LocalDataSource
    fun provideLocalUserConfigsRepository(
        sharedPrefUtility: SharedPrefUtility<UserConfigsDto>
    ): UserConfigsRepository = LocalUserConfigsRepository(sharedPrefUtility)
}
