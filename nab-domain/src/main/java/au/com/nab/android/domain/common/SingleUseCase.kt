package au.com.nab.android.domain.common

import io.reactivex.Single

abstract class SingleUseCase<in Params, Result> protected constructor() {

    protected abstract fun preExecute(params: Params): Single<Result>

    /**
     * Executes the current use case.
     */
    fun execute(params: Params) = this.preExecute(params)

}
