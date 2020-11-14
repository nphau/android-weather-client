package au.com.nab.android.shared.libs.imageloader

import android.content.Context
import android.widget.ImageView
import au.com.nab.android.shared.common.ParameterizedSingleton
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HandImageLoader private constructor(val context: Context) {

    private var loader: ImageCaching? = null
    private var executorService: ExecutorService? = null

    companion object : ParameterizedSingleton<HandImageLoader, Context>(::HandImageLoader) {
        internal var screenWidth = 0
        internal var screenHeight = 0
    }

    init {
        val metrics = context.resources.displayMetrics
        screenWidth = metrics.widthPixels
        screenHeight = metrics.heightPixels
        // Initialize disk cache
        cacheStrategy(CacheStrategy.DISK)
    }

    private fun threadPoolTags(): Pair<Int, String> {
        return when (loader) {
            is DiskLruLoader -> {
                Pair(CacheStrategy.DISK.threadPool(), DiskLruLoader::class.java.name)
            }
            else -> {
                Pair(CacheStrategy.MEMORY.threadPool(), MemLruLoader::class.java.name)
            }
        }
    }

    fun cacheStrategy(source: CacheStrategy): HandImageLoader {
        loader = when (source) {
            CacheStrategy.DISK -> DiskLruLoader(context)
            else -> MemLruLoader(context)
        }
        // Define executor service
        val (threadPool, tag) = threadPoolTags()
        executorService = Executors.newFixedThreadPool(threadPool, PriorityThreadFactory(tag))
        return this
    }

    fun onProgress(callBack: (Long) -> Unit): HandImageLoader {
        loader?.onProgress(callBack)
        return this
    }

    fun load(imageView: ImageView, imageUrl: String?) {
        if (imageUrl.isNullOrEmpty())
            return
        loader?.load(imageView, imageUrl)
    }

    fun flush() {
        executorService?.execute { loader?.flush() }
    }

    fun close() {
        executorService?.execute { loader?.close() }
    }
}