package au.com.nab.android.shared.libs.imageloader;

import android.widget.ImageView
import androidx.annotation.IntRange
import au.com.nab.android.shared.R
import com.bumptech.glide.Priority
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.*
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import vn.futagroup.android.shared.common.extensions.isAvailable
import java.util.*

class GlideLoader : IImageLoader {

    override fun <T> load(imageView: ImageView, resource: T) {
        loadDefault(imageView, resource, R.drawable.ic_image_holder, TransformType.NORMAL)
    }

    override fun <T> load(imageView: ImageView, resource: T, @TransformType transformType: Int) {
        loadDefault(imageView, resource, R.drawable.ic_image_holder, transformType)
    }

    override fun loadWithHeaders(
        imageView: ImageView,
        url: String,
        headers: LinkedHashMap<String, String>
    ) {
        val builder = LazyHeaders.Builder()
        for ((key, value) in headers)
            builder.addHeader(key, value)
        val glideUrl = GlideUrl(url, builder.build())
        loadDefault(
            imageView, glideUrl, R.drawable.ic_image_holder,
            TransformType.NORMAL
        )
    }

    override fun loadWithHeaders(
        imageView: ImageView,
        url: String,
        headers: LinkedHashMap<String, String>, @TransformType transformType: Int
    ) {
        val builder = LazyHeaders.Builder()
        for ((key, value) in headers) {
            builder.addHeader(key, value)
        }
        val glideUrl = GlideUrl(url, builder.build())
        loadDefault(imageView, glideUrl, R.drawable.ic_image_holder, transformType)
    }

    override fun <T> loadDefault(
        imageView: ImageView,
        resource: T,
        defaultDrawable: Int, @TransformType transformType: Int
    ) {
        if (!imageView.context.isAvailable()) return
        when (transformType) {
            TransformType.NORMAL -> GlideApp.with(imageView.context)
                .load(resource)
                .apply(noAnimation())
                .apply(noTransformation())
                .apply(placeholderOf(defaultDrawable))
                .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(priorityOf(Priority.IMMEDIATE))
                .apply(errorOf(defaultDrawable))
                .into(imageView)
            TransformType.AVATAR,
            TransformType.CIRCLE -> GlideApp.with(imageView.context)
                .load(resource)
                .apply(placeholderOf(defaultDrawable))
                .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(priorityOf(Priority.IMMEDIATE))
                .apply(errorOf(defaultDrawable))
                .apply(bitmapTransform(MultiTransformation(CenterCrop(), CircleCrop())))
                .into(imageView)
            TransformType.FIT_CENTER -> GlideApp.with(imageView.context)
                .load(resource)
                .apply(fitCenterTransform())
                .apply(noAnimation())
                .apply(noTransformation())
                .apply(placeholderOf(defaultDrawable))
                .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(priorityOf(Priority.IMMEDIATE))
                .apply(errorOf(defaultDrawable))
                .into(imageView)
            TransformType.ROUND_CORNER -> roundWithDefault(imageView, resource, defaultDrawable, 16)
        }

    }

    override fun <T> loadDefaultOverride(
        imageView: ImageView, resource: T, defaultDrawable: Int,
        @IntRange(from = 0) sizeOfOverride: Int,
        @TransformType transformType: Int
    ) {
        when (transformType) {
            TransformType.NORMAL -> normalWithDefaultOverride(
                imageView,
                resource,
                defaultDrawable,
                sizeOfOverride
            )
            TransformType.AVATAR,
            TransformType.CIRCLE -> circleWithDefaultOverride(
                imageView,
                resource,
                defaultDrawable,
                sizeOfOverride
            )
            TransformType.FIT_CENTER -> fitCenterWithDefaultOverride(
                imageView,
                resource,
                defaultDrawable,
                sizeOfOverride
            )
            TransformType.ROUND_CORNER -> roundWithDefault(
                imageView,
                resource,
                defaultDrawable,
                sizeOfOverride
            )
        }
    }

    override fun <T> normalWithDefaultOverride(
        imageView: ImageView,
        resource: T,
        defaultDrawable: Int, @IntRange(from = 0) sizeOfOverride: Int
    ) {
        if (!imageView.context.isAvailable()) return
        GlideApp.with(imageView.context)
            .load(resource)
            .apply(overrideOf(sizeOfOverride))
            .apply(noAnimation())
            .apply(noTransformation())
            .apply(placeholderOf(defaultDrawable))
            .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .apply(priorityOf(Priority.IMMEDIATE))
            .apply(errorOf(defaultDrawable))
            .into(imageView)
    }

    override fun <T> circleWithDefaultOverride(
        imageView: ImageView,
        resource: T,
        defaultDrawable: Int, @IntRange(from = 0) sizeOfOverride: Int
    ) {
        if (!imageView.context.isAvailable()) return
        GlideApp.with(imageView.context)
            .load(resource)
            .apply(overrideOf(sizeOfOverride))
            .apply(placeholderOf(defaultDrawable))
            .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .apply(priorityOf(Priority.IMMEDIATE))
            .apply(errorOf(defaultDrawable))
            .apply(bitmapTransform(MultiTransformation(CenterCrop(), CircleCrop())))
            .into(imageView)
    }

    override fun <T> fitCenterWithDefaultOverride(
        imageView: ImageView,
        resource: T,
        defaultDrawable: Int, @IntRange(from = 0) sizeOfOverride: Int
    ) {
        if (!imageView.context.isAvailable()) return
        GlideApp.with(imageView.context)
            .load(resource)
            .apply(overrideOf(sizeOfOverride))
            .apply(fitCenterTransform())
            .apply(noAnimation())
            .apply(noTransformation())
            .apply(placeholderOf(defaultDrawable))
            .apply(diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .apply(priorityOf(Priority.IMMEDIATE))
            .apply(errorOf(defaultDrawable))
            .into(imageView)
    }

    override fun <T> roundWithDefault(
        imageView: ImageView,
        resource: T,
        defaultDrawable: Int,
        radius: Int
    ) {
        if (!imageView.context.isAvailable()) return
        GlideApp.with(imageView.context)
            .load(resource)
            .apply(
                RequestOptions()
                    .placeholder(defaultDrawable)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(defaultDrawable)
                    .priority(Priority.IMMEDIATE)
                    .transform(CenterCrop(), RoundedCorners(radius))
            )
            .apply(noAnimation())
            .into(imageView)
    }

    override fun <T> roundWithTransition(
        imageView: ImageView,
        resource: T,
        defaultDrawable: Int,
        radius: Int
    ) {
        if (!imageView.context.isAvailable()) return
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        GlideApp.with(imageView.context)
            .load(resource)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(defaultDrawable)
            .error(defaultDrawable)
            .priority(Priority.IMMEDIATE)
            .transition(withCrossFade(factory))
            .transform(CenterCrop(), RoundedCorners(radius))
            .into(imageView)
    }
}

