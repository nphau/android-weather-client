package au.com.nab.android.shared.common.exceptions.errors

import retrofit2.HttpException

fun Throwable?.toServerError(): ServerError {
    if (this is HttpException) {
        val errorBody = response()?.errorBody()?.string()
        val (errorCode, message) = ErrorHandling.parseServerError(errorBody)
        return ServerError.error(errorCode, message, this)
    }
    return ServerError.error(this)
}