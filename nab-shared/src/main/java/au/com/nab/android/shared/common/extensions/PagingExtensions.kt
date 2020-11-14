package au.com.nab.android.shared.common.extensions

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun CombinedLoadStates.isLoading(): Boolean {
    return refresh is LoadState.Loading || append is LoadState.Loading
}

fun CombinedLoadStates.isNotLoading(): Boolean {
    return refresh is LoadState.NotLoading
}

fun CombinedLoadStates.isError(): Boolean {
    return refresh is LoadState.Error
}

fun CombinedLoadStates.isEndOfPaginationReached(): Boolean {
    return isNotLoading() && append.endOfPaginationReached
}

fun CombinedLoadStates.error(): Throwable {
    return (refresh as LoadState.Error).error
}
