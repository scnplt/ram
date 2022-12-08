/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.core.data.repository.MaterialRepository
import dev.sertan.android.ram.core.data.repository.QuestionRepository
import dev.sertan.android.ram.core.data.repository.RamMaterialRepository
import dev.sertan.android.ram.core.data.repository.RamQuestionRepository
import dev.sertan.android.ram.core.data.repository.RamUserSettingsRepository
import dev.sertan.android.ram.core.data.repository.UserSettingsRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindMaterialRepository(
        materialRepository: RamMaterialRepository
    ): MaterialRepository

    @Binds
    abstract fun bindQuestionRepository(
        questionRepository: RamQuestionRepository
    ): QuestionRepository

    @Binds
    abstract fun bindUserSettingsRepository(
        userSettingsRepository: RamUserSettingsRepository
    ): UserSettingsRepository
}
