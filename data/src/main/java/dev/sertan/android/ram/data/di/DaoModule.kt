/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.data.database.RamDatabase
import dev.sertan.android.ram.data.database.dao.MaterialDao
import dev.sertan.android.ram.data.database.dao.QuestionDao
import dev.sertan.android.ram.data.database.dao.QuestionWithMaterialsDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    @Singleton
    fun provideQuestionDao(ramDatabase: RamDatabase): QuestionDao {
        return ramDatabase.questionDao()
    }

    @Provides
    @Singleton
    fun provideMaterialDao(ramDatabase: RamDatabase): MaterialDao {
        return ramDatabase.materialDao()
    }

    @Provides
    @Singleton
    fun provideQuestionWithMaterialsDao(ramDatabase: RamDatabase): QuestionWithMaterialsDao {
        return ramDatabase.questionWithMaterialsDao()
    }
}
