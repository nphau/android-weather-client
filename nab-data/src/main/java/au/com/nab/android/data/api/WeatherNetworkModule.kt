package au.com.nab.android.data.api

import au.com.nab.android.data.NetworkEndpointCoordinator
import au.com.nab.android.data.common.HeaderType
import au.com.nab.android.shared.common.Singleton
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Named

class WeatherNetworkModule @Inject constructor() : SharedNetworkModule() {

    @Inject
    @Named(HeaderType.LOGGER)
    lateinit var loggerInterceptor: Interceptor

    @Inject
    @Named(HeaderType.UN_AUTHORIZATION)
    lateinit var unAuthorizationInterceptor: Interceptor

    companion object : Singleton<WeatherNetworkModule>(::WeatherNetworkModule)

    fun userService() = getService<WeatherNetworkService>()

    override fun interceptors(): List<Interceptor> {
        return listOf(loggerInterceptor, unAuthorizationInterceptor)
    }

    override fun getBaseUrl(): String {
        return NetworkEndpointCoordinator.BASE_URL
    }

}