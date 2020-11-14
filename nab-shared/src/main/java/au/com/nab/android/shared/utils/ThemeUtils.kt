package au.com.nab.android.shared.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import au.com.nab.android.shared.R
import org.jetbrains.anko.doFromSdk

object ThemeUtils {

    @JvmStatic
    fun clearLightStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = activity.window.decorView.systemUiVisibility
            flags = flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.decorView.systemUiVisibility = flags
            activity.window.statusBarColor = ContextCompat
                .getColor(activity, R.color.colorPrimaryDark)
        }
    }

    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources
            .getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0)
            result = context.resources.getDimensionPixelSize(resourceId)
        return result
    }

    @JvmStatic
    fun getScreenHeight(paramContext: Context): Int {
        return paramContext.resources.displayMetrics.heightPixels
    }

    @JvmStatic
    fun getScreenWidth(paramContext: Context): Int {
        return paramContext.resources.displayMetrics.widthPixels
    }

    @JvmStatic
    fun setWindowFlag(activity: Activity, bits: Int, isOn: Boolean) {
        val window = activity.window
        val winParams = window.attributes
        if (isOn) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv() // ~bits
        }
        window.attributes = winParams
    }

    @JvmStatic
    fun getScreenSize(context: Context): Point {
        val windowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

    @JvmStatic
    @TargetApi(Build.VERSION_CODES.M)
    fun fullscreenWithStatusBarColor(activity: Activity, color: Int = 0) {
        val window = activity.window
        if (color != 0) {
            doFromSdk(Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.statusBarColor = ContextCompat.getColor(activity, color)
            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            // Make fully Android Transparent Status bar
            doFromSdk(Build.VERSION_CODES.LOLLIPOP) {
                setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
                window.statusBarColor = Color.TRANSPARENT
            }
        }
    }

    @JvmStatic
    @TargetApi(Build.VERSION_CODES.M)
    fun fullscreenWithStatusBarColor(
        activity: Activity, color: Int = 0, @IntRange(from = 0, to = 255) statusBarAlpha: Int
    ) {
        val window = activity.window
        if (color != 0) {
            doFromSdk(Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.statusBarColor = calculateStatusColor(color, statusBarAlpha)
            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Make fully Android Transparent Status bar
            doFromSdk(Build.VERSION_CODES.LOLLIPOP) {
                setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
                window.statusBarColor = Color.TRANSPARENT
            }
        }
    }

    @JvmStatic
    fun redrawStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = ContextCompat.getColor(activity, R.color.colorPrimaryDark)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
    }

    @JvmStatic
    fun setLightStatusBar(activity: Activity, color: Int = Color.WHITE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                statusBarColor = color
            }
        }
    }

    @JvmStatic
    fun setDarkStatusBar(activity: Activity, color: Int = Color.WHITE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                statusBarColor = color
            }
        }
    }

    @JvmStatic
    fun calculateStatusColor(@ColorInt color: Int, alpha: Int): Int {
        if (alpha == 0)
            return color
        val a = 1 - alpha / 255f
        var red = color shr 16 and 0xff
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
    }

    @JvmStatic
    fun isDarkTheme(context: Context) = context.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    @JvmStatic
    fun isLightTheme(context: Context) = !isDarkTheme(context)

    @JvmStatic
    fun setNightMode(forceNight: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (forceNight) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}