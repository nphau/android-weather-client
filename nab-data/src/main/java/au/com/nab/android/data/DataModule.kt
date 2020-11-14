package au.com.nab.android.data

import au.com.nab.android.data.api.UnAuthorizationHeaderInterceptor
import au.com.nab.android.data.api.WeatherNetworkModule
import au.com.nab.android.data.api.WeatherNetworkService
import au.com.nab.android.data.common.HeaderType
import au.com.nab.android.data.repositories.SearchWeathersRepositoryImpl
import au.com.nab.android.domain.repositories.SearchWeathersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [DataRepositoryModule::class])
class DataModule {

    @Singleton
    @Provides
    fun provideGithubUserNetworkService(): WeatherNetworkService {
        return WeatherNetworkModule.getInstance().userService()
    }

    @Singleton
    @Provides
    @Named(HeaderType.UN_AUTHORIZATION)
    fun provideUnAuthorizationHeader(): Interceptor {
        return UnAuthorizationHeaderInterceptor()
    }

    @Singleton
    @Provides
    @Named(HeaderType.LOGGER)
    fun provideLoggerHeader(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
        }
    }

}

@Module
interface DataRepositoryModule {

    @Binds
    @Singleton
    fun bindSearchWeathersRepository(searchWeathersRepositoryImpl: SearchWeathersRepositoryImpl): SearchWeathersRepository
    
}