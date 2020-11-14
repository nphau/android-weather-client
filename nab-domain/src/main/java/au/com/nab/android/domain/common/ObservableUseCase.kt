package au.com.nab.android.domain.common

import io.reactivex.Observable

abstract class ObservableUseCase<in Params, Result> protected constructor() {

    protected abstract fun preExecute(params: Params): Observable<Result>

    fun execute(params: Params) = this.preExecute(params)

}
