package au.com.nab.android.data.repositories

import au.com.nab.android.data.Keys
import au.com.nab.android.data.api.WeatherNetworkService
import au.com.nab.android.data.db.dao.WeathersDao
import au.com.nab.android.data.entities.WeatherResponseEntity
import au.com.nab.android.data.transform.EntitiesTransformer
import au.com.nab.android.domain.entities.Weather
import au.com.nab.android.domain.repositories.SearchWeathersRepository
import au.com.nab.android.shared.common.exceptions.errors.ServerError
import au.com.nab.android.shared.common.exceptions.errors.toServerError
import au.com.nab.android.shared.common.functional.Result
import au.com.nab.android.shared.libs.network.NetworkHandler
import io.reactivex.Observable
import javax.inject.Inject

class SearchWeathersRepositoryImpl @Inject constructor(
    private val networkService: WeatherNetworkService,
    private val weathersDao: WeathersDao,
    private val networkHandler: NetworkHandler
) : SharedRepository(), SearchWeathersRepository {

    override fun searchWeather(query: HashMap<String, Any>): Observable<Result<List<Weather>, ServerError>> {

        query[Keys.UNIT] = "Metric"
        query[Keys.APP_ID] = Keys.apiKey()

        if (networkHandler.isNetworkAvailable()) {
            return networkService.searchWeather(query)
                .doOnNext {
                    weathersDao.insertAll(
                        WeatherResponseEntity(
                            city = it.city,
                            items = it.items,
                            cityName = query[Keys.QUERY] as String
                        )
                    )
                }
                .map { response ->
                    if (response.isSuccess()) {
                        val items = response.items
                            ?.map { EntitiesTransformer.fromWeatherEntitiesToWeather(it) }
                            ?: emptyList()
                        Result.Success(items)
                    } else {
                        Result.Failure(ServerError.error(response.message))
                    }
                }
                .onErrorReturn { Result.Failure(it.toServerError()) }
        } else {
            // Load data from cache with keyword is city-name
            return weathersDao.findByCityName(query[Keys.QUERY] as String)
                .toObservable()
                .map { response ->
                    Result.Success(response.items
                        ?.map { EntitiesTransformer.fromWeatherEntitiesToWeather(it) }
                        ?: emptyList())
                }
        }
    }
}