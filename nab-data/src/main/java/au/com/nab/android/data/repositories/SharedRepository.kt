package au.com.nab.android.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource

abstract class SharedRepository {

    open fun getPageSize(): Int = 10

    open fun getPageHintSize(): Int = 10

    open fun getInitialLoadSize(): Int = 10

    open fun getPrefetchDistance(): Int = 10

    open fun enablePlaceholders(): Boolean = false

    open fun getPagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = getPageSize(),
            initialLoadSize = getInitialLoadSize(),
            prefetchDistance = getPrefetchDistance()
        )
    }

    protected open fun <Key : Any, Value : Any> createPagerConfig(source: PagingSource<Key, Value>): Pager<Key, Value> {
        return Pager(config = getPagingConfig(), pagingSourceFactory = { source })
    }

}
