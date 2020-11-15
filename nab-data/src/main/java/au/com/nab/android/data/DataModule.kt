package au.com.nab.android.data

import android.content.Context
import androidx.room.Room
import au.com.nab.android.data.api.WeatherNetworkModule
import au.com.nab.android.data.api.WeatherNetworkService
import au.com.nab.android.data.db.LocalDB
import au.com.nab.android.data.repositories.SearchWeathersRepositoryImpl
import au.com.nab.android.domain.repositories.SearchWeathersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
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
    fun provideDb(context: Context): LocalDB {
        return Room
            .databaseBuilder(context, LocalDB::class.java, "app.local.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}

@Module
interface DataRepositoryModule {

    @Binds
    @Singleton
    fun bindSearchWeathersRepository(searchWeathersRepositoryImpl: SearchWeathersRepositoryImpl): SearchWeathersRepository

}