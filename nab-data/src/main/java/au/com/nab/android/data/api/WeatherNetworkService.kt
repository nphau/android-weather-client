package au.com.nab.android.data.api

import au.com.nab.android.data.entities.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherNetworkService {

    @GET("forecast/daily")
    fun searchWeather(@QueryMap query: HashMap<String, Any>): Observable<WeatherResponse>

}