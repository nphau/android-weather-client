package au.com.nab.android.data.api

import au.com.nab.android.data.BuildConfig
import au.com.nab.android.data.NetworkEndpointCoordinator
import au.com.nab.android.shared.common.Singleton
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class WeatherNetworkModule @Inject constructor() : SharedNetworkModule() {

    companion object : Singleton<WeatherNetworkModule>(::WeatherNetworkModule)

    fun userService() = getService<WeatherNetworkService>()

    override fun modifiedInterceptors() =
        listOf(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
        })

    override fun modifiedNetworkInterceptors() =
        listOf(StethoInterceptor())

    override fun getBaseUrl() = NetworkEndpointCoordinator.BASE_URL

}