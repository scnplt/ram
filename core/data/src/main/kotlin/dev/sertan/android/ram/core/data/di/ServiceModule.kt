/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.ram.core.data.service.MaterialService
import dev.sertan.android.ram.core.data.service.QuestionService
import dev.sertan.android.ram.core.data.service.firebase.FirestoreMaterialService
import dev.sertan.android.ram.core.data.service.firebase.FirestoreQuestionService

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ServiceModule {

    @Binds
    abstract fun bindMaterialService(materialService: FirestoreMaterialService): MaterialService

    @Binds
    abstract fun bindQuestionService(questionService: FirestoreQuestionService): QuestionService
}
