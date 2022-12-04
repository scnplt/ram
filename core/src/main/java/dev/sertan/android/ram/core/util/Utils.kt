/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.util

import timber.log.Timber

@Suppress("TooGenericExceptionCaught")
suspend fun <R> tryGetResultWithLog(block: suspend () -> R): Result<R> = try {
    Result.success(block())
} catch (exception: Throwable) {
    Timber.e(exception)
    Result.failure(exception)
}

@Suppress("MagicNumber")
fun percent(value: Float, total: Float): Float = if (total == 0f) 0f else value / total * 100
