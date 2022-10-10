/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.di.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.data.database.LocalDatabase
import dev.sertan.android.ram.data.datasource.LessonDataSource
import dev.sertan.android.ram.data.datasource.MaterialDataSource
import dev.sertan.android.ram.data.datasource.QuestionDataSource
import javax.inject.Named
import javax.inject.Singleton

internal const val LOCAL_DATASOURCE_INJECT_NAME = "local-datasource-injection-name"

@Module
@InstallIn(SingletonComponent::class)
internal object DataSourceModule {

    @Provides
    @Singleton
    @Named(LOCAL_DATASOURCE_INJECT_NAME)
    fun provideLocalLessonDataSource(localDatabase: LocalDatabase): LessonDataSource {
        return localDatabase.lessonDao()
    }

    @Provides
    @Singleton
    @Named(LOCAL_DATASOURCE_INJECT_NAME)
    fun provideLocalQuestionDataSource(localDatabase: LocalDatabase): QuestionDataSource {
        return localDatabase.questionDao()
    }

    @Provides
    @Singleton
    @Named(LOCAL_DATASOURCE_INJECT_NAME)
    fun provideLocalMaterialDataSource(localDatabase: LocalDatabase): MaterialDataSource {
        return localDatabase.materialDao()
    }
}
