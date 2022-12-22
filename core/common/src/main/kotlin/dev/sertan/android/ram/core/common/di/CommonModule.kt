/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.common.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.core.common.log.RamLogger
import dev.sertan.android.ram.core.common.log.TimberRamLogger
import java.util.Locale
import javax.inject.Singleton

const val DEFAULT_LOCALE_LANG_TAG = "tr-TR"

@Module
@InstallIn(SingletonComponent::class)
internal object CommonModule {

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideRamLogger(logger: TimberRamLogger): RamLogger = logger

    @Provides
    @Singleton
    fun provideLocale(): Locale = Locale(DEFAULT_LOCALE_LANG_TAG)
}
