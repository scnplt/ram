/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.feature.material.data.di

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
import dev.sertan.android.ram.feature.material.data.datasource.local.MaterialDao
import dev.sertan.android.ram.feature.material.data.datasource.local.RamDatabase
import dev.sertan.android.ram.feature.material.data.datasource.remote.NetworkMaterial
import javax.inject.Singleton

private const val MATERIAL_SUFFIX = "material"

@Module
@InstallIn(SingletonComponent::class)
internal object DatasourceModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RamDatabase =
        Room.databaseBuilder(context, RamDatabase::class.java, RamDatabase::class.java.name).build()

    @Provides
    @Singleton
    fun provideMaterialDao(database: RamDatabase): MaterialDao = database.materialDao()

    @Provides
    @Singleton
    fun provideFirestoreMaterialDataReceiver(
        @CollectionPath collectionPath: String,
        firestore: FirebaseFirestore
    ): FirestoreDataReceiver<NetworkMaterial> = FirestoreDataReceiver(
        firestore = firestore,
        collectionName = "$collectionPath/$MATERIAL_SUFFIX",
        dataClass = NetworkMaterial::class.java
    )
}
