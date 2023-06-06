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
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.appmemory.data.datasource.local.RamDatabase
import dev.sertan.android.ram.appmemory.data.datasource.local.dao.GapFillingQuestionDao
import dev.sertan.android.ram.appmemory.data.datasource.local.dao.MatchingQuestionDao
import dev.sertan.android.ram.appmemory.data.datasource.remote.NetworkGapFillingQuestion
import dev.sertan.android.ram.appmemory.data.datasource.remote.NetworkMatchingQuestion
import dev.sertan.android.ram.core.data.FirestoreDataReceiver
import dev.sertan.android.ram.core.data.di.CollectionPath
import javax.inject.Singleton

private const val GAP_FILLING_SUFFIX = "filling"
private const val MATCHING_SUFFIX = "matching"

@Module
@InstallIn(SingletonComponent::class)
internal object DatasourceModule {

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

    @Provides
    @Singleton
    fun provideFirestoreGapFillingQuestionDataReceiver(
        @CollectionPath collectionPath: String,
        firestore: FirebaseFirestore
    ): FirestoreDataReceiver<NetworkGapFillingQuestion> = FirestoreDataReceiver(
        firestore = firestore,
        collectionName = "$collectionPath/$GAP_FILLING_SUFFIX",
        dataClass = NetworkGapFillingQuestion::class.java
    )

    @Provides
    @Singleton
    fun provideFirestoreMatchingQuestionDataReceiver(
        @CollectionPath collectionPath: String,
        firestore: FirebaseFirestore
    ): FirestoreDataReceiver<NetworkMatchingQuestion> = FirestoreDataReceiver(
        firestore = firestore,
        collectionName = "$collectionPath/$MATCHING_SUFFIX",
        dataClass = NetworkMatchingQuestion::class.java
    )
}
