package vn.futagroup.android.shared.common.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import java.io.Serializable

fun Map<String, Any?>.toBundle(): Bundle = bundleOf(*this.toList().toTypedArray())

fun <T> Bundle.put(key: String, value: T) {
    when (value) {
        is IBinder -> putBinder(key, value)
        is Bundle -> putBundle(key, value)
        is Byte -> putByte(key, value)
        is ByteArray -> putByteArray(key, value)
        is Char -> putChar(key, value)
        is CharArray -> putCharArray(key, value)
        is CharSequence -> putCharSequence(key, value)
        is Float -> putFloat(key, value)
        is FloatArray -> putFloatArray(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        is Short -> putShort(key, value)
        is ShortArray -> putShortArray(key, value)
        else -> throw IllegalStateException("Type of property $key is not supported")
    }
}

inline fun <reified T> Any.ofType(block: (T) -> Unit) {
    if (this is T) {
        block(this as T)
    }
}

/**
 * https://stackoverflow.com/a/50915146/2198705
 * Return true if this [Context] is available.
 * Availability is defined as the following:
 * + [Context] is not null
 * + [Context] is not destroyed (tested with [FragmentActivity.isDestroyed] or [Activity.isDestroyed])
 */
fun Context?.isAvailable(): Boolean {
    if (this == null) {
        return false
    } else if (this !is Application) {
        if (this is FragmentActivity) {
            return !this.isDestroyed
        } else if (this is Activity) {
            return !this.isDestroyed
        }
    }
    return true
}
