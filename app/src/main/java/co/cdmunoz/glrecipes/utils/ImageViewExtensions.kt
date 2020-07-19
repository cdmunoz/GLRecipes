package co.cdmunoz.glrecipes.utils

import android.widget.ImageView

fun ImageView.setImageFromUrl(imageUrl: String) {
    GlideApp.with(context).load(imageUrl).thumbnail(0.1f).into(this)
}

fun ImageView.setImageFromResource(resourceId: Int) {
    GlideApp.with(context).load(resourceId).thumbnail(0.1f).into(this)
}