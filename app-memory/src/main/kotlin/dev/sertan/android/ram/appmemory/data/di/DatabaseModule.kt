/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.appmemory.data.datasource.local.RamDatabase
import dev.sertan.android.ram.appmemory.data.datasource.local.dao.GapFillingQuestionDao
import dev.sertan.android.ram.appmemory.data.datasource.local.dao.MatchingQuestionDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RamDatabase =
        Room.databaseBuilder(context, RamDatabase::class.java, RamDatabase::class.java.name).build()

    @Provides
    @Singleton
    fun provideGapFillingQuestionDao(database: RamDatabase): GapFillingQuestionDao =
        database.gapFillingQuestionDao()

    @Provides
    @Singleton
    fun provideMatchingQuestionDao(database: RamDatabase): MatchingQuestionDao =
        database.matchingQuestionDao()
}
