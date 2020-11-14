package au.com.nab.android.domain.usecases

import au.com.nab.android.domain.common.ObservableUseCase
import au.com.nab.android.domain.entities.Weather
import au.com.nab.android.domain.repositories.SearchWeathersRepository
import au.com.nab.android.shared.common.exceptions.errors.ServerError
import au.com.nab.android.shared.common.functional.Result
import io.reactivex.Observable
import javax.inject.Inject

class SearchWeathersUseCase @Inject constructor(private val repository: SearchWeathersRepository) :
    ObservableUseCase<HashMap<String, Any>, Result<List<Weather>, ServerError>>() {

    override fun preExecute(params: HashMap<String, Any>): Observable<Result<List<Weather>, ServerError>> {
        return repository.searchWeather(params)
    }
}