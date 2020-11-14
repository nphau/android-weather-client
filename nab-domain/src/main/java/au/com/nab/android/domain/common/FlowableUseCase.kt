package au.com.nab.android.domain.common

import io.reactivex.Flowable

abstract class FlowableUseCase<in Params, Result> protected constructor() {

    protected abstract fun preExecute(params: Params): Flowable<Result>

    fun execute(params: Params) = this.preExecute(params)
}
