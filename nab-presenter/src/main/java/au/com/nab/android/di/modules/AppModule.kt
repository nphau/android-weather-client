package au.com.nab.android.di.modules

import androidx.lifecycle.ViewModel
import au.com.nab.android.screens.activities.MainActivity
import au.com.nab.android.screens.fragments.MainFragment
import au.com.nab.android.shared.di.ViewModelKey
import au.com.nab.android.vm.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface AppModule {

    // region [ViewModel]
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    // region [Activity]
    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    // endregion

    // region [Fragment]
    @ContributesAndroidInjector
    fun contributeMainFragment(): MainFragment
    // endregion

}