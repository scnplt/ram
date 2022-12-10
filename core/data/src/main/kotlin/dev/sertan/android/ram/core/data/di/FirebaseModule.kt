/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.di

import android.content.Context
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.core.common.getAppModuleName
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class FirebaseCollection(val type: CollectionType)

internal enum class CollectionType { MATERIAL, QUESTION }

@Module
@InstallIn(SingletonComponent::class)
internal object FirebaseModule {

    @Provides
    @Singleton
    @FirebaseCollection(CollectionType.MATERIAL)
    fun provideMaterialCollection(@ApplicationContext context: Context): CollectionReference =
        Firebase.firestore.collection(getCollectionNameByAppName(context, CollectionType.MATERIAL))

    @Provides
    @Singleton
    @FirebaseCollection(CollectionType.QUESTION)
    fun provideQuestionCollection(@ApplicationContext context: Context): CollectionReference =
        Firebase.firestore.collection(getCollectionNameByAppName(context, CollectionType.QUESTION))

    private fun getCollectionNameByAppName(context: Context, type: CollectionType): String =
        "${context.getAppModuleName()}-${type.name.lowercase()}"
}
