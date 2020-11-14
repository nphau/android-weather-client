package au.com.nab.android.domain.repositories

import au.com.nab.android.domain.entities.Weather
import au.com.nab.android.shared.common.exceptions.errors.ServerError
import au.com.nab.android.shared.common.functional.Result
import io.reactivex.Observable

interface SearchWeathersRepository {

    fun searchWeather(query: HashMap<String, Any>): Observable<Result<List<Weather>, ServerError>>

}