package au.com.nab.android.shared.common.exceptions.errors

import java.net.HttpURLConnection

data class ServerError(
    val errorCode: Int = HttpURLConnection.HTTP_INTERNAL_ERROR,
    override val message: String? = null,
    override val cause: Throwable? = null
) : Throwable(message, cause) {

    companion object {

        fun error(throwable: Throwable?): ServerError {
            return ServerError(cause = throwable)
        }

        fun error(errorMessage: String?): ServerError {
            return ServerError(message = errorMessage)
        }

        fun error(errorCode: Int, message: String?): ServerError {
            return ServerError(errorCode, message)
        }

        fun error(errorCode: Int, message: String?, cause: Throwable?): ServerError {
            return ServerError(errorCode, message, cause)
        }
    }

    fun isServerError(): Boolean {
        return errorCode == HttpURLConnection.HTTP_INTERNAL_ERROR
    }

    override fun toString(): String {
        return "[$errorCode] $message"
    }
}