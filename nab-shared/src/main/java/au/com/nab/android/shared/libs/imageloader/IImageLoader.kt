package au.com.nab.android.shared.libs.imageloader

import android.widget.ImageView
import androidx.annotation.IntRange
import java.util.*

interface IImageLoader {

    fun <T> load(imageView: ImageView, resource: T)

    fun <T> load(imageView: ImageView, resource: T, @TransformType transformType: Int)

    fun loadWithHeaders(imageView: ImageView, url: String, headers: LinkedHashMap<String, String>)

    fun <T> loadDefault(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int, @TransformType transformType: Int
    )

    fun loadWithHeaders(
            imageView: ImageView,
            url: String,
            headers: LinkedHashMap<String, String>, @TransformType transformType: Int
    )

    fun <T> normalWithDefaultOverride(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int, @IntRange(from = 0) sizeOfOverride: Int
    )

    fun <T> circleWithDefaultOverride(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int, @IntRange(from = 0) sizeOfOverride: Int
    )

    fun <T> fitCenterWithDefaultOverride(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int, @IntRange(from = 0) sizeOfOverride: Int
    )

    fun <T> roundWithDefault(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int, @IntRange(from = 0) radius: Int
    )

    fun <T> loadDefaultOverride(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int, @IntRange(from = 0) sizeOfOverride: Int,
            @TransformType transformType: Int
    )

    fun <T> roundWithTransition(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int,
            radius: Int
    )
}
