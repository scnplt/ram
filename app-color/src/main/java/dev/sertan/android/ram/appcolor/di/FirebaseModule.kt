/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.di

import android.content.Context
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val MATERIAL_COLLECTION_REF_NAME = "materials"
private const val QUESTION_COLLECTION_REF_NAME = "questions"

@Module
@InstallIn(SingletonComponent::class)
internal object FirebaseModule {

    @Provides
    @Singleton
    @MaterialCollection
    fun provideMaterialCollectionReference(
        @ApplicationContext context: Context
    ): CollectionReference = Firebase.firestore
        .collection("${context.packageName}-$MATERIAL_COLLECTION_REF_NAME")

    @Provides
    @Singleton
    @QuestionCollection
    fun provideQuestionCollectionReference(
        @ApplicationContext context: Context
    ): CollectionReference = Firebase.firestore
        .collection("${context.packageName}-$QUESTION_COLLECTION_REF_NAME")
}
