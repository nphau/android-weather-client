package au.com.nab.android.shared.common.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import au.com.nab.android.shared.libs.imageloader.ImageLoader
import au.com.nab.android.shared.screens.views.OnItemClickListener
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Set an onclick listener
 */

inline fun <T : View> T.safeClick(crossinline block: () -> Unit): Disposable {
    return RxView.clicks(this)
        .throttleFirst(850, TimeUnit.MILLISECONDS)
        .subscribe({ block() }, { })
}

fun View?.visible() {
    if (this != null) {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
    }
}

fun View?.gone() {
    if (this != null) {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
    }
}
// region [RecyclerView]

fun RecyclerView.onItemClick(callback: (view: View, position: Int) -> Unit) {
    addOnItemTouchListener(OnItemClickListener(context, callback))
}
// endregion
// region [TextView]

@BindingAdapter("android:text")
fun TextView.formatHtml(@StringRes resId: Int = -1) {
    if (resId == -1) {
        text = null
    } else {
        formatHtml(context.getString(resId))
    }
}

@BindingAdapter("formatHtml")
fun TextView.formatHtml(value: String? = "") {
    text = value?.formatHtml() ?: ""
}
// endregion
// region [AppCompatImageView]

@BindingAdapter("srcCompat")
fun AppCompatImageView.srcCompat(@DrawableRes resId: Int = -1) {
    if (resId != -1) setImageResource(resId)
}

@BindingAdapter("srcCompat")
fun AppCompatImageView.srcCompat(drawable: Drawable?) {
    try { // I do not want to use ApplicationContext, so use try-catch
        drawable?.let { ImageLoader.plug().load(this, drawable) }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@BindingAdapter(value = ["srcCompat", "applyAvatar"], requireAll = false)
fun AppCompatImageView.srcCompat(
    data: String?,
    applyAvatar: Boolean = false
) {
    try { // I do not want to use ApplicationContext, so use try-catch
        when {
            applyAvatar -> ImageLoader.plug().loadAvatar(this, data)
            else -> ImageLoader.plug().load(this, data)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
// endregion