/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.di.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.data.database.dao.LessonDao
import dev.sertan.android.ram.data.database.dao.LessonDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideLessonDatabase(@ApplicationContext context: Context): LessonDatabase {
        return Room.databaseBuilder(
            context,
            LessonDatabase::class.java,
            LessonDatabase::class.java.name
        ).build()
    }

    @Provides
    @Singleton
    fun provideLessonDao(lessonDatabase: LessonDatabase): LessonDao {
        return lessonDatabase.lessonDao()
    }
}

