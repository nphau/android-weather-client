package au.com.nab.android.data.api

import au.com.nab.android.data.common.NullOnEmptyConverterFactory
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class SharedNetworkModule {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(getOkHttpClient())
            .addConverterFactory(NullOnEmptyConverterFactory.create())
            .addConverterFactory(requireGSON())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    inline fun <reified S> getService(): S {
        return getRetrofit().create(S::class.java)
    }

    open fun requireGSON(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().create())

    open fun getOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder().apply {
            this.followRedirects(true)
                .followSslRedirects(true)
                .readTimeout(readTimeout(), TimeUnit.SECONDS)
                .writeTimeout(writeTimeout(), TimeUnit.SECONDS)
                .connectTimeout(connectTimeout(), TimeUnit.SECONDS)
            addInterceptor(UnAuthorizationHeaderInterceptor())
            modifiedInterceptors().forEach { this.addInterceptor(it) }
            modifiedNetworkInterceptors().forEach { this.addNetworkInterceptor(it) }
        }
        return okHttpBuilder.build()
    }

    abstract fun modifiedInterceptors(): List<Interceptor>

    open fun modifiedNetworkInterceptors(): List<Interceptor> = emptyList()

    abstract fun getBaseUrl(): String

    open fun writeTimeout(): Long = NetworkConfig.WRITE_TIMEOUT

    open fun connectTimeout(): Long = NetworkConfig.CONNECT_TIMEOUT

    open fun readTimeout(): Long = NetworkConfig.READ_TIMEOUT
}
