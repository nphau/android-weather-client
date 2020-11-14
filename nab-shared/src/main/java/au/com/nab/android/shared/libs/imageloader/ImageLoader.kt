package au.com.nab.android.shared.libs.imageloader

import android.widget.ImageView
import au.com.nab.android.shared.R
import java.util.*

class ImageLoader private constructor() : IImageLoader {

    private var imageLoaderInterface: IImageLoader? = null

    fun setImageLoader(imageLoaderInterface: IImageLoader): ImageLoader? {
        this.imageLoaderInterface = imageLoaderInterface
        return imageLoader
    }

    private fun setDefaultLoader() {
        this.imageLoaderInterface = GlideLoader()
    }

    private fun getDefault(): IImageLoader? {
        if (imageLoaderInterface == null)
            setDefaultLoader()
        return imageLoaderInterface
    }

    override fun <T> load(imageView: ImageView, resource: T) {
        getDefault()?.load(imageView, resource)
    }

    override fun <T> load(imageView: ImageView, resource: T, transformType: Int) {
        getDefault()?.load(imageView, resource, transformType)
    }

    override fun loadWithHeaders(imageView: ImageView, url: String, headers: LinkedHashMap<String, String>) {
        getDefault()?.loadWithHeaders(imageView, url, headers)
    }

    override fun loadWithHeaders(imageView: ImageView, url: String, headers: LinkedHashMap<String, String>, transformType: Int) {
        getDefault()?.loadWithHeaders(imageView, url, headers, transformType)
    }

    override fun <T> loadDefault(imageView: ImageView, resource: T, defaultDrawable: Int, transformType: Int) {
        getDefault()?.loadDefault(imageView, resource, defaultDrawable, transformType)
    }

    override fun <T> loadDefaultOverride(imageView: ImageView, resource: T,
                                         defaultDrawable: Int, sizeOfOverride: Int,
                                         transformType: Int) {
        getDefault()?.loadDefaultOverride(imageView, resource, defaultDrawable, sizeOfOverride, transformType)
    }

    override fun <T> roundWithTransition(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int,
            radius: Int
    ) {
        getDefault()?.roundWithTransition(
                imageView,
                resource,
                defaultDrawable,
                radius
        )
    }

    override fun <T> normalWithDefaultOverride(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int,
            sizeOfOverride: Int
    ) {
        getDefault()?.normalWithDefaultOverride(
                imageView,
                resource,
                defaultDrawable,
                sizeOfOverride
        )
    }

    override fun <T> circleWithDefaultOverride(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int,
            sizeOfOverride: Int
    ) {
        getDefault()?.circleWithDefaultOverride(
                imageView,
                resource,
                defaultDrawable,
                sizeOfOverride
        )
    }

    override fun <T> fitCenterWithDefaultOverride(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int,
            sizeOfOverride: Int
    ) {
        getDefault()?.fitCenterWithDefaultOverride(
                imageView,
                resource,
                defaultDrawable,
                sizeOfOverride
        )
    }

    override fun <T> roundWithDefault(
            imageView: ImageView,
            resource: T,
            defaultDrawable: Int,
            radius: Int
    ) {
        getDefault()?.roundWithDefault(imageView, resource, defaultDrawable, radius)
    }

    // region Extension

    fun <T> loadAvatar(imageView: ImageView, resource: T) {
        getDefault()?.loadDefault(imageView, resource, R.drawable.ic_avatar_holder,
            TransformType.CIRCLE
        )
    }

    fun <T> loadRoundCorners(imageView: ImageView, resource: T) {
        getDefault()?.loadDefault(imageView, resource, R.drawable.ic_image_holder,
            TransformType.ROUND_CORNER
        )
    }

    // endregion

    companion object {

        private var imageLoader: ImageLoader? = null

        @JvmStatic
        fun plug(): ImageLoader {
            if (imageLoader == null)
                imageLoader = ImageLoader()
            return imageLoader!!
        }
    }
}