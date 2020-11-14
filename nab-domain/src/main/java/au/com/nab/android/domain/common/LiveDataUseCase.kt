package au.com.nab.android.domain.common

import androidx.lifecycle.LiveData

abstract class LiveDataUseCase<in Params, Result> protected constructor() {

    protected abstract fun preExecute(params: Params): LiveData<Result>

    /**
     * Executes the current use case.
     */
    fun execute(params: Params) = this.preExecute(params)

}
