/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.common

import android.content.Context
import dev.sertan.android.ram.core.common.log.RamLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

@Suppress("MagicNumber")
fun calculatePercentage(part: Float, total: Float): Float {
    return if (total == 0f) 0f else part / total * 100
}

fun Context.getAppModuleName(): String = packageName.split(".").last()

inline fun tryWithLogger(logger: RamLogger, block: () -> Unit): Boolean = try {
    block()
    true
} catch (exception: Exception) {
    logger.e(exception)
    false
}

fun <I, O> List<I>.tryMapNotNull(logger: RamLogger? = null, transform: (I) -> O): List<O> =
    mapNotNull {
        try {
            transform(it)
        } catch (exception: Exception) {
            logger?.e(exception)
            null
        }
    }

inline fun <T> tryGetWithResult(logger: RamLogger? = null, block: () -> T): Result<T> = try {
    Result.success(block())
} catch (exception: Exception) {
    logger?.e(exception)
    Result.failure(exception)
}

inline fun <I, O> Flow<I>.tryMapWithResult(
    logger: RamLogger? = null,
    crossinline block: (I) -> O
): Flow<Result<O>> = map { Result.success(block(it)) }.catch { exception ->
    logger?.e(exception)
    Result.failure<O>(exception)
}
