/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.feature.question.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.feature.question.data.database.QuestionDatabase
import dev.sertan.android.feature.question.data.database.dao.QuestionDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuestionDatabase =
        Room.databaseBuilder(
            context,
            QuestionDatabase::class.java,
            QuestionDatabase::class.java.name
        ).build()

    @Provides
    @Singleton
    fun provideQuestionDao(database: QuestionDatabase): QuestionDao = database.questionDao()
}
