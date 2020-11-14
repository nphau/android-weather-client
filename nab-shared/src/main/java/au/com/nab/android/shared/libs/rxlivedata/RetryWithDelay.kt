package au.com.nab.android.shared.libs.rxlivedata

import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

class RetryWithDelay(private val maxRetries: Int, private val retryDelayMillis: Int) :
    Function<Observable<out Throwable?>, Observable<*>> {

    private var retryCount = 0

    override fun apply(attempts: Observable<out Throwable?>): Observable<*> {
        return attempts
            .flatMap(Function<Throwable?, Observable<*>> { throwable: Throwable? ->
                if (++retryCount < maxRetries) {
                    // When this Observable calls onNext, the original
                    // Observable will be retried (i.e. re-subscribed).
                    return@Function Observable.timer(
                        retryDelayMillis.toLong(),
                        TimeUnit.MILLISECONDS
                    )
                }
                Observable.error<Any?>(throwable)
            })
    }

}
