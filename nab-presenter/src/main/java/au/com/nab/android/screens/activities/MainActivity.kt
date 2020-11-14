package au.com.nab.android.screens.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import au.com.nab.android.R
import au.com.nab.android.databinding.ActivityMainBinding
import au.com.nab.android.shared.libs.rxlivedata.observe
import au.com.nab.android.shared.screens.activities.BindingSharedActivity
import au.com.nab.android.vm.MainUiEffect
import au.com.nab.android.vm.MainViewModel
import javax.inject.Inject

class MainActivity : BindingSharedActivity<ActivityMainBinding>(R.layout.activity_main) {

    // region [Inject]
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }
    // endregion

    private lateinit var navController: NavController

    override fun onSyncViews(savedInstanceState: Bundle?) {
        super.onSyncViews(savedInstanceState)
        setUpNavigation()
    }

    override fun onSyncEvents() {
        super.onSyncEvents()
        observe(viewModel.uiState) { effect ->
            when (effect) {
                is MainUiEffect.GoBack -> onBackPressed()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun setUpNavigation() {
        navController = findNavController(R.id.graph_main)
    }

    override fun allowUserDismissKeyboardWhenClickOutSide(): Boolean = true
}