package dev.sertan.android.ram.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.coredomain.di.LocalDataSource
import dev.sertan.android.ram.coredomain.repository.MaterialRepository
import dev.sertan.android.ram.data.database.dao.MaterialDao
import dev.sertan.android.ram.data.database.dao.QuestionWithMaterialsDao
import dev.sertan.android.ram.data.repository.local.LocalMaterialRepository
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
    ): MaterialRepository {
        return LocalMaterialRepository(materialDao, questionWithMaterialsDao)
    }
}
