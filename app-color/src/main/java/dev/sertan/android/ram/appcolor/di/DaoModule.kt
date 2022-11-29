/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.appcolor.data.database.ColorAppDatabase
import dev.sertan.android.ram.appcolor.data.database.dao.MaterialDao
import dev.sertan.android.ram.appcolor.data.database.dao.QuestionDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    @Singleton
    fun provideQuestionDao(colorAppDatabase: ColorAppDatabase): QuestionDao =
        colorAppDatabase.questionDao()

    @Provides
    @Singleton
    fun provideMaterialDao(colorAppDatabase: ColorAppDatabase): MaterialDao =
        colorAppDatabase.materialDao()
}
