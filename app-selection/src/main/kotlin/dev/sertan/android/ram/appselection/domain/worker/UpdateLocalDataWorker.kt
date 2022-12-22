/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appselection.domain.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.sertan.android.ram.appselection.domain.usecase.RefreshLocalDataUseCase
import java.util.concurrent.TimeUnit

@HiltWorker
internal class UpdateLocalDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val refreshLocalDataUseCase: RefreshLocalDataUseCase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result =
        if (refreshLocalDataUseCase()) Result.success() else Result.retry()

    companion object {
        private const val REPEAT_INTERVAL_DAY = 1L
        private const val BACKOFF_DELAY_MIN = 15L

        fun start(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

            val request = PeriodicWorkRequestBuilder<UpdateLocalDataWorker>(
                REPEAT_INTERVAL_DAY,
                TimeUnit.DAYS
            ).setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.LINEAR, BACKOFF_DELAY_MIN, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                UpdateLocalDataWorker::class.java.name,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
