package au.com.nab.android.data.api

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UnAuthorizationHeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return chain.proceed(
            original.newBuilder()
                .headers(createHeaders())
                .method(original.method, original.body)
                .build()
        )
    }

    protected open fun createHeaders(): Headers {
        return Headers.Builder()
            .add("x-platform", "android")
            .add("Content-Type", "application/json; charset=UTF-8")
            .build()
    }
}