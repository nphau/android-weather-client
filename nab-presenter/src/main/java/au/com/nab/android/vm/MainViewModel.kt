package au.com.nab.android.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import au.com.nab.android.domain.entities.Weather
import au.com.nab.android.shared.common.extensions.SingleLiveEvent
import au.com.nab.android.shared.common.functional.Result
import au.com.nab.android.shared.vm.SharedViewModel
import javax.inject.Inject

typealias Response = Result<List<Weather>, Throwable>

class MainViewModel @Inject constructor(
) : SharedViewModel() {

    private val _weathers: MutableLiveData<Response> = MutableLiveData()
    var weathers: LiveData<Response> = _weathers

    private val _uiState: MutableLiveData<MainUiEffect> = SingleLiveEvent()
    var uiState: LiveData<MainUiEffect> = _uiState

    fun searchWeather(query: String) {

    }

    fun loadRecentUsers() {

    }

    fun goBack() {
        dispatchEvent(MainUiEffect.GoBack)
    }

    private fun dispatchEvent(effect: MainUiEffect) {
        _uiState.postValue(effect)
    }

}