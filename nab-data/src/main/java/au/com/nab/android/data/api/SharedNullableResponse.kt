package au.com.nab.android.data.api

import java.net.HttpURLConnection

sealed class SharedResponse<T>

data class SharedNullableResponse<T>(
    val errorCode: String? = "",
    val message: String? = "",
    val status: Int = -1,
    var data: T?
) : SharedResponse<T>() {

    companion object Factory {
        fun <T> success(t: T): SharedNullableResponse<T> {
            return SharedNullableResponse(status = HttpURLConnection.HTTP_OK, data = t)
        }
    }

    fun isSuccess(): Boolean {
        return status == HttpURLConnection.HTTP_OK
    }
}

class SharedEmptyResponse<T> : SharedResponse<T>()
data class SharedErrorResponse<T>(val errorMessage: String) : SharedResponse<T>()
