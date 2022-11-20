/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coredomain.worker

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
import dev.sertan.android.ram.coredomain.usecase.UpdateLocalMaterialsUseCase
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class UpdateLocalMaterialsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val updateLocalMaterialsUseCase: UpdateLocalMaterialsUseCase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        if (updateLocalMaterialsUseCase()) Result.success() else Result.retry()
    }

    companion object {
        private const val REPEAT_INTERVAL_DAY = 1L
        private const val BACKOFF_DELAY_MIN = 15L

        fun uniqueStart(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

            val request = PeriodicWorkRequestBuilder<UpdateLocalMaterialsWorker>(
                REPEAT_INTERVAL_DAY,
                TimeUnit.DAYS
            ).setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.LINEAR, BACKOFF_DELAY_MIN, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                UpdateLocalMaterialsWorker::class.java.name,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
