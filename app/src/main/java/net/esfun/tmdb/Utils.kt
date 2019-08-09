package net.esfun.tmdb

import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class MyAppGlideModule : AppGlideModule()


fun <T> ImageView.loadUsualImage(
    model: T
) {
    GlideApp.with(context)
        .asBitmap()
        .load(model)
        .fitCenter()
        //.transform(CenterCrop())
        .into(this)
}
