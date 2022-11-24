/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredata.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.corecommon.repository.MaterialRepository
import dev.sertan.android.ram.corecommon.repository.QuestionRepository
import dev.sertan.android.ram.corecommon.repository.UserSettingsRepository
import dev.sertan.android.ram.coredata.repository.MaterialRepositoryImpl
import dev.sertan.android.ram.coredata.repository.QuestionRepositoryImpl
import dev.sertan.android.ram.coredata.repository.UserSettingsRepositoryImpl

@Suppress("UnnecessaryAbstractClass")
@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun provideMaterialRepository(
        materialRepository: MaterialRepositoryImpl
    ): MaterialRepository

    @Binds
    abstract fun provideUserSettingsRepository(
        userSettingsRepository: UserSettingsRepositoryImpl
    ): UserSettingsRepository

    @Binds
    abstract fun provideQuestionRepository(
        questionRepository: QuestionRepositoryImpl
    ): QuestionRepository
}
