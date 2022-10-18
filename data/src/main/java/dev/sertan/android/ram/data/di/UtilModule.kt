/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.data.di

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.corecommon.model.UserConfigsDto
import dev.sertan.android.ram.corecommon.util.JsonConverter
import dev.sertan.android.ram.data.util.SharedPrefUtility
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UtilModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideJsonConverter(moshi: Moshi): JsonConverter<UserConfigsDto> {
        return with(moshi.adapter(UserConfigsDto::class.java)) {
            JsonConverter.create(toJson = ::toJson, fromJson = ::fromJson)
        }
    }

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPrefUtility(
        sharedPref: SharedPreferences,
        jsonConverter: JsonConverter<UserConfigsDto>
    ): SharedPrefUtility<UserConfigsDto> = SharedPrefUtility(
        sharedPreferences = sharedPref,
        jsonConverter = jsonConverter,
        dataClass = UserConfigsDto::class.java,
        defaultKey = UserConfigsDto::class.java.name
    )
}
