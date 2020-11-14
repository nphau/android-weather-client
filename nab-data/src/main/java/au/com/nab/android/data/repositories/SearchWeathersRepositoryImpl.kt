package au.com.nab.android.data.repositories

import au.com.nab.android.data.api.WeatherNetworkService
import au.com.nab.android.data.transform.EntitiesTransformer
import au.com.nab.android.domain.entities.Weather
import au.com.nab.android.domain.repositories.SearchWeathersRepository
import au.com.nab.android.shared.common.exceptions.errors.ServerError
import au.com.nab.android.shared.common.exceptions.errors.toServerError
import au.com.nab.android.shared.common.functional.Result
import io.reactivex.Observable
import javax.inject.Inject

class SearchWeathersRepositoryImpl @Inject constructor(
    private val networkService: WeatherNetworkService
) : SharedRepository(), SearchWeathersRepository {
    override fun searchWeather(query: HashMap<String, Any>): Observable<Result<List<Weather>, ServerError>> {
        return networkService.searchWeather(query)
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
    }
}