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
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.appnumber.data.datasource.local.NumberDatabase
import dev.sertan.android.ram.appnumber.data.datasource.local.SectionDao
import dev.sertan.android.ram.appnumber.data.datasource.remote.NetworkSection
import dev.sertan.android.ram.core.data.FirestoreDataReceiver
import dev.sertan.android.ram.core.data.di.CollectionPath
import javax.inject.Singleton

private const val SECTION_SUFFIX = "section"

@Module
@InstallIn(SingletonComponent::class)
internal object DatasourceModule {

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
    fun provideSectionDao(database: NumberDatabase): SectionDao = database.sectionDao()

    @Provides
    @Singleton
    fun provideFirestoreSectionDataReceiver(
        @CollectionPath collectionPath: String,
        firestore: FirebaseFirestore
    ): FirestoreDataReceiver<NetworkSection> = FirestoreDataReceiver(
        firestore = firestore,
        collectionName = "$collectionPath/$SECTION_SUFFIX",
        dataClass = NetworkSection::class.java
    )
}
