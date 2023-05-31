/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.appmemory.data.repository.DefaultGapFillingQuestionRepository
import dev.sertan.android.ram.appmemory.data.repository.DefaultMatchingQuestionRepository
import dev.sertan.android.ram.appmemory.domain.repository.GapFillingQuestionRepository
import dev.sertan.android.ram.appmemory.domain.repository.MatchingQuestionRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindGapFillingQuestionQuestionRepository(
        repository: DefaultGapFillingQuestionRepository
    ): GapFillingQuestionRepository

    @Binds
    abstract fun bindMatchingQuestionRepository(
        repository: DefaultMatchingQuestionRepository
    ): MatchingQuestionRepository
}
