/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.appnumber.data.service.MaterialService
import dev.sertan.android.ram.appnumber.data.service.QuestionService
import dev.sertan.android.ram.appnumber.data.service.SectionService
import dev.sertan.android.ram.appnumber.data.service.firestore.FirestoreMaterialService
import dev.sertan.android.ram.appnumber.data.service.firestore.FirestoreQuestionService
import dev.sertan.android.ram.appnumber.data.service.firestore.FirestoreSectionService

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ServiceModule {

    @Binds
    abstract fun bindMaterialService(service: FirestoreMaterialService): MaterialService

    @Binds
    abstract fun bindQuestionService(service: FirestoreQuestionService): QuestionService

    @Binds
    abstract fun bindSectionService(service: FirestoreSectionService): SectionService
}
