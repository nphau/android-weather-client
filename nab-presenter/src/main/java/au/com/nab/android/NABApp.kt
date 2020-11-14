package au.com.nab.android

import au.com.nab.android.shared.SharedApp
import au.com.nab.android.shared.libs.logger.ReleaseLoggingTree
import au.com.nab.android.di.DaggerAppComponent
import au.com.nab.android.libs.CrashlyticsLoggingTree
import au.com.nab.android.shared.utils.ThemeUtils
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasAndroidInjector

class NABApp : SharedApp(), HasAndroidInjector {

    override fun onCreate() {
        super.onCreate()
        // Disable night mode
        ThemeUtils.setNightMode(false)
    }

    override fun releaseLoggingTree(): ReleaseLoggingTree {
        return CrashlyticsLoggingTree()
    }

    override fun isDebugMode(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}