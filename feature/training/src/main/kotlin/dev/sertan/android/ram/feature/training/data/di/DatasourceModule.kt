/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.training.data.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.core.data.FirestoreDataReceiver
import dev.sertan.android.ram.core.data.di.CollectionPath
import dev.sertan.android.ram.feature.training.data.datasource.local.QuestionDao
import dev.sertan.android.ram.feature.training.data.datasource.local.RamDatabase
import dev.sertan.android.ram.feature.training.data.datasource.remote.NetworkQuestion
import javax.inject.Singleton

private const val QUESTION_SUFFIX = "question"

@Module
@InstallIn(SingletonComponent::class)
internal object DatasourceModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RamDatabase =
        Room.databaseBuilder(context, RamDatabase::class.java, RamDatabase::class.java.name).build()

    @Provides
    @Singleton
    fun provideQuestionDao(database: RamDatabase): QuestionDao = database.questionDao()

    @Provides
    @Singleton
    fun provideFirestoreQuestionDataReceiver(
        @CollectionPath collectionPath: String,
        firestore: FirebaseFirestore
    ): FirestoreDataReceiver<NetworkQuestion> = FirestoreDataReceiver(
        firestore = firestore,
        collectionName = "$collectionPath/$QUESTION_SUFFIX",
        dataClass = NetworkQuestion::class.java
    )
}
