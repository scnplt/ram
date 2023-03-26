/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.util

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Priority
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import dev.sertan.android.ram.core.ui.R

private const val DISK_CACHE_SIZE_BYTES = 1024 * 1024 * 100L
private const val TIMEOUT = 1000 * 5
private const val SIZE_MULTIPLIER = 0.6f

@GlideModule
class RamGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder): Unit = builder.run {
        setDiskCache(InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE_BYTES))
        setDefaultRequestOptions(getRequestOptions())
    }

    private fun getRequestOptions(): RequestOptions = RequestOptions()
        .priority(Priority.HIGH)
        .sizeMultiplier(SIZE_MULTIPLIER)
        .error(R.drawable.ic_warning)
        .placeholder(R.drawable.ic_refresh)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .timeout(TIMEOUT)
}
