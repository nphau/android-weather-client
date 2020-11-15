package au.com.nab.android.screens.fragments

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import au.com.nab.android.R
import au.com.nab.android.databinding.FragmentMainBinding
import au.com.nab.android.screens.adapters.WeathersAdapters
import au.com.nab.android.shared.common.autoCleared
import au.com.nab.android.shared.common.exceptions.SharedExceptions
import au.com.nab.android.shared.common.extensions.gone
import au.com.nab.android.shared.common.extensions.hideKeyboardIfNeed
import au.com.nab.android.shared.common.extensions.safeClick
import au.com.nab.android.shared.common.extensions.visible
import au.com.nab.android.shared.libs.rxlivedata.applyFormValidator
import au.com.nab.android.shared.libs.rxlivedata.observe
import au.com.nab.android.shared.libs.rxlivedata.safeDispose
import au.com.nab.android.shared.screens.fragments.BindingSharedFragment
import au.com.nab.android.vm.MainViewModel
import com.jakewharton.rxbinding2.widget.afterTextChangeEvents
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainFragment : BindingSharedFragment<FragmentMainBinding>(R.layout.fragment_main) {

    // region [Inject]
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by activityViewModels { viewModelFactory }

    // endregion
    private var afterTextChanged: Disposable? = null
    private var weathersAdapters by autoCleared<WeathersAdapters>()

    override fun onSyncViews(savedInstanceState: Bundle?) {
        super.onSyncViews(savedInstanceState)
        // Previous
        binding.toolbar.onLeftClickListener { viewModel.goBack() }
        weathersAdapters = WeathersAdapters()
        setUpRecyclerView()
    }

    override fun onSyncEvents() {
        super.onSyncEvents()

        addTextChangeListener()

        observe(viewModel.weathers) { weathersResponse ->
            weathersResponse.fold(
                { weathers ->
                    dismissLoading()
                    weathersAdapters.submitList(weathers)
                    if (weathersAdapters.itemCount > 0) {
                        binding.viewEmpty.hide()
                        binding.recyclerView.visible()
                    } else {
                        binding.viewEmpty.show()
                        binding.recyclerView.gone()
                    }
                },
                {
                    dismissLoading()
                    if (it !is SharedExceptions.NoNetworkConnection) {
                        showError(it.message)
                    }
                },
                { showLoading() })
        }
        binding.button.safeClick { viewModel.searchWeather(binding.editQuery.text.toString()) }
    }

    override fun showLoading() {
        binding.progressBar.show()
    }

    override fun onDestroyView() {
        safeDispose()
        super.onDestroyView()
    }

    override fun showError(message: String?) {
        binding.viewEmpty.setMessage(message ?: getString(R.string.common_error_connect))
        binding.viewEmpty.show()
        binding.recyclerView.gone()
    }

    override fun dismissLoading() {
        binding.progressBar.hide()
    }

    private fun setUpRecyclerView() = with(binding.recyclerView) {
        setHasFixedSize(true)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter = weathersAdapters
    }

    private fun addTextChangeListener() {

        safeDispose()

        afterTextChanged = binding.editQuery.afterTextChangeEvents()
            .skipInitialValue()
            .compose(applyFormValidator())
            .map { it.editable().toString() }
            .subscribe(
                { binding.button.isEnabled = it.length >= 3 },
                { it.printStackTrace() })

        binding.editQuery.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboardIfNeed()
                viewModel.searchWeather(binding.editQuery.text.toString())
                true
            } else {
                false
            }
        }
    }

    private fun safeDispose() {
        afterTextChanged.safeDispose()
    }
}