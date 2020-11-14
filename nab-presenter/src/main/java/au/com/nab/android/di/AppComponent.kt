package au.com.nab.android.di

import au.com.nab.android.NABApp
import au.com.nab.android.data.DataModule
import au.com.nab.android.di.modules.AppModule
import au.com.nab.android.shared.di.modules.SharedAppModule
import au.com.nab.android.shared.di.scope.ApplicationScope
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        SharedAppModule::class,
        AndroidSupportInjectionModule::class]
)
interface AppComponent : AndroidInjector<NABApp> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<NABApp>

}