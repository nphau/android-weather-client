package au.com.nab.android.shared.di.modules

import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import au.com.nab.android.shared.SharedApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [SharedSchedulerModule::class, SharedViewModelModule::class])
object SharedAppModule {

    @Singleton
    @Provides
    fun provideContext(): Context = SharedApp.getContext()

    @Provides
    fun provideClipboardManager(context: Context): ClipboardManager =
        context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    @Provides
    fun provideConnectivityManager(context: Context): ConnectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

}