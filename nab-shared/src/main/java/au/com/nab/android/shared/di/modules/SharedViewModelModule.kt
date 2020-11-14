package au.com.nab.android.shared.di.modules

import androidx.lifecycle.ViewModelProvider
import au.com.nab.android.shared.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface SharedViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
