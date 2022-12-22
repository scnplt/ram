/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.appselection.data.repository.DefaultMaterialRepository
import dev.sertan.android.ram.appselection.data.repository.DefaultQuestionRepository
import dev.sertan.android.ram.appselection.domain.repository.MaterialRepository
import dev.sertan.android.ram.appselection.domain.repository.QuestionRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindMaterialRepository(
        materialRepository: DefaultMaterialRepository
    ): MaterialRepository

    @Binds
    abstract fun bindQuestionRepository(
        questionRepository: DefaultQuestionRepository
    ): QuestionRepository
}
