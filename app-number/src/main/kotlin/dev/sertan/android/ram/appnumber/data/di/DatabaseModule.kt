/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.appnumber.data.database.NumberDatabase
import dev.sertan.android.ram.appnumber.data.database.dao.MaterialDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NumberDatabase =
        Room.databaseBuilder(
            context,
            NumberDatabase::class.java,
            NumberDatabase::class.java.name
        ).build()

    @Provides
    @Singleton
    fun provideMaterialDao(database: NumberDatabase): MaterialDao = database.materialDao()
}
