package au.com.nab.android.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import au.com.nab.android.domain.entities.Weather
import au.com.nab.android.domain.usecases.SearchWeathersUseCase
import au.com.nab.android.shared.common.extensions.SingleLiveEvent
import au.com.nab.android.shared.common.functional.Result
import au.com.nab.android.shared.libs.rxlivedata.addTo
import au.com.nab.android.shared.libs.rxlivedata.applyObservableIoScheduler
import au.com.nab.android.shared.vm.SharedViewModel
import javax.inject.Inject

typealias Response = Result<List<Weather>, Throwable>

class MainViewModel @Inject constructor(
    private val searchWeathersUseCase: SearchWeathersUseCase
) : SharedViewModel() {

    private val _weathers: MutableLiveData<Response> = MutableLiveData()
    var weathers: LiveData<Response> = _weathers

    private val _uiState: MutableLiveData<MainUiEffect> = SingleLiveEvent()
    var uiState: LiveData<MainUiEffect> = _uiState

    fun searchWeather(query: String) {
        searchWeathersUseCase.execute(
            hashMapOf(
                "q" to query,
                "cnt" to 7,
                "appid" to "60c6fbeb4b93ac653c492ba806fc346d",
                "units" to "Metric"
            )
        )
            .compose(applyObservableIoScheduler())
            .doOnSubscribe { _weathers.postValue(Result.Loading) }
            .subscribe(
                { _weathers.postValue(it) },
                { _weathers.postValue(Result.Failure(it)) })
            .addTo(getCompositeDisposable())
    }

    fun goBack() {
        dispatchEvent(MainUiEffect.GoBack)
    }

    private fun dispatchEvent(effect: MainUiEffect) {
        _uiState.postValue(effect)
    }

}