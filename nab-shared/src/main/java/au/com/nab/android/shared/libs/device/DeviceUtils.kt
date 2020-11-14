package au.com.nab.android.shared.libs.device

import android.app.ActivityManager
import android.content.Context

object DeviceUtils {

    private const val OPTIMUM_CORE = 4
    private const val OPTIMUM_MEMORY_MB = 124

    fun isHighPerformingDevice(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return !activityManager.isLowRamDevice &&
                Runtime.getRuntime().availableProcessors() >= OPTIMUM_CORE &&
                activityManager.memoryClass >= OPTIMUM_MEMORY_MB
    }

}