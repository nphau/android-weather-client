package au.com.nab.android.shared.screens.adapters

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class CorePagingDataAdapter<T : Any>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    open fun get(position: Int): T? {
        if (position < 0 || position > itemCount)
            return null
        return super.getItem(position)
    }


    fun isEmpty(): Boolean {
        return itemCount == 0
    }
}
