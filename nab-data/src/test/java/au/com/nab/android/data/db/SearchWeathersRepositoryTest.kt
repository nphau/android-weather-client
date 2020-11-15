package au.com.nab.android.data.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import au.com.nab.android.data.Keys
import au.com.nab.android.data.api.WeatherNetworkService
import au.com.nab.android.data.repositories.SearchWeathersRepositoryImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchWeathersRepositoryTest {

    private lateinit var weatherNetworkService: WeatherNetworkService
    private lateinit var searchWeathersRepositoryImpl: SearchWeathersRepositoryImpl

    @Before
    fun setup() {
        searchWeathersRepositoryImpl = SearchWeathersRepositoryImpl(weatherNetworkService)
    }

    @Test
    fun searchWeather() {
        val query = "saigon"
        searchWeathersRepositoryImpl.searchWeather(
            hashMapOf(
                Keys.QUERY to query,
                Keys.CNT to 7,
                Keys.UNIT to "Metric",
                Keys.APP_ID to Keys.apiKey()
            )
        ).test()
    }
}