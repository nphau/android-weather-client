package au.com.nab.android.shared.libs.rxlivedata

import androidx.lifecycle.*

fun <T : Any, L : LiveData<T>> LifecycleOwner.observeNullable(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))


/**
 * Adds the given observer to the observers list within the lifespan of the given
 * owner. The events are dispatched on the main thread. If LiveData already has data
 * set, it will be delivered to the observer.
 *
 * @param liveData The mutableLiveData to observe.
 * @param observer The observer that will receive the events.
 * @see MutableLiveData.observe
 */
fun <T> LifecycleOwner.observe(liveData: MutableLiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, Observer {
        it?.let { t -> observer(t) }
    })
}

/**
 * observeOnce
 * */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <T> LifecycleOwner.observeOnce(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observeOnce(this, {
        it?.let { t -> observer(t) }
    })
}

/**
 * Adds the given observer to the observers list within the lifespan of the given
 * owner. The events are dispatched on the main thread. If LiveData already has data
 * set, it will be delivered to the observer.
 *
 * @param liveData The liveData to observe.
 * @param observer The observer that will receive the events.
 * @see LiveData.observe
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, Observer {
        it?.let { t -> observer(t) }
    })
}

/**
 * Emits the items that are different from all the values that have been emitted so far
 */
fun <T> LiveData<T>.distinct(): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    val dispatchedValues = mutableListOf<T?>()
    mutableLiveData.addSource(this) {
        if (!dispatchedValues.contains(it)) {
            mutableLiveData.value = it
            dispatchedValues.add(it)
        }
    }
    return mutableLiveData
}


/**
 * Emits the items that are different from the last item
 */
fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    var latestValue: T? = null
    mutableLiveData.addSource(this) {
        if (latestValue != it) {
            mutableLiveData.value = it
            latestValue = it
        }
    }
    return mutableLiveData
}


/**
 * Merges this LiveData with another one, and emits any item that was emitted by any of them
 */
fun <T> LiveData<T>.mergeWith(vararg liveDatas: LiveData<T>): LiveData<T> {
    val mergeWithArray = mutableListOf<LiveData<T>>()
    mergeWithArray.add(this)
    mergeWithArray.addAll(liveDatas)
    return mergeWithArray.merge()
}

/**
 * Merges multiple LiveData, and emits any item that was emitted by any of them
 */
fun <T> List<LiveData<T>>.merge(): LiveData<T> {
    val finalLiveData: MediatorLiveData<T> = MediatorLiveData()
    forEach { liveData ->

        liveData.value?.let {
            finalLiveData.value = it
        }

        finalLiveData.addSource(liveData) { source ->
            finalLiveData.value = source
        }
    }
    return finalLiveData
}

/**
 * Emits the `startingValue` before any other value.
 */
fun <T> LiveData<T>.startWith(startingValue: T?): LiveData<T> {
    val finalLiveData: MediatorLiveData<T> = MediatorLiveData()
    finalLiveData.value = startingValue
    finalLiveData.addSource(this) { source ->
        finalLiveData.value = source
    }
    return finalLiveData
}
