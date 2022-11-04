/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.coreui.util

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Priority
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import dev.sertan.android.ram.coreui.R

private const val MEM_CACHE_SIZE_BYTES = 1024 * 1024 * 10L
private const val DISK_CACHE_SIZE_BYTES = 1024 * 1024 * 50L
private const val TIMEOUT = 5 * 1000
private const val ENCODE_QUALITY = 100

@GlideModule
class RamGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder): Unit = builder.run {
        setMemoryCache(LruResourceCache(MEM_CACHE_SIZE_BYTES))
        setDiskCache(InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE_BYTES))
        setDefaultRequestOptions(getRequestOptions())
    }

    private fun getRequestOptions(): RequestOptions = RequestOptions()
        .priority(Priority.HIGH)
        .error(R.drawable.ic_warning)
        .placeholder(R.drawable.ic_refresh)
        .encodeQuality(ENCODE_QUALITY)
        .signature(ObjectKey(System.currentTimeMillis()))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .timeout(TIMEOUT)
}
