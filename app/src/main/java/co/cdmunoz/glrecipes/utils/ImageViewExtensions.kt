package co.cdmunoz.glrecipes.utils

import android.widget.ImageView

fun ImageView.setImageFromUrl(imageUrl: String) {
    GlideApp.with(context).load(imageUrl).thumbnail(0.1f).into(this)
}