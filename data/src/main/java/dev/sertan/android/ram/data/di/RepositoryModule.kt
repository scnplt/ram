package dev.sertan.android.ram.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import dev.sertan.android.ram.corecommon.repository.UserSettingsRepository
import dev.sertan.android.ram.data.repository.local.LocalMaterialRepository
import dev.sertan.android.ram.data.repository.local.LocalUserSettingsRepository

@Suppress("UnnecessaryAbstractClass")
@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindMaterialRepository(
        materialRepository: LocalMaterialRepository
    ): MaterialRepository

    @Binds
    abstract fun bindUserSettingsRepository(
        userSettingsRepository: LocalUserSettingsRepository
    ): UserSettingsRepository
}
