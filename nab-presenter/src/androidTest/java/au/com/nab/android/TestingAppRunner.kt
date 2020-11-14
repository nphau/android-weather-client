package au.com.nab.android

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TestingAppRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader,
        className: String,
        context: Context
    ): Application {
        return super.newApplication(classLoader, TestingApp::class.java.name, context)
    }
}