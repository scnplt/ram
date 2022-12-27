/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.feature.question.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.android.feature.question.data.service.FirestoreQuestionService
import dev.sertan.android.feature.question.data.service.QuestionService

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ServiceModule {

    @Binds
    abstract fun bindQuestionService(service: FirestoreQuestionService): QuestionService
}
