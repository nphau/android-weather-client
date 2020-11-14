package au.com.nab.android.domain.common

import io.reactivex.Completable

abstract class CompletableUseCase<in Params> protected constructor() {

    protected abstract fun preExecute(params: Params): Completable

    fun execute(params: Params) = this.preExecute(params)
}
