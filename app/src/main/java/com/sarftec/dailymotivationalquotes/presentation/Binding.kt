package com.sarftec.dailymotivationalquotes.presentation

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.card.MaterialCardView
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.application.imagestore.ImageHolder
import kotlin.reflect.KProperty

class Bindable<T : Any>(private var value: T, private val tag: Int) {
    operator fun <U : BaseObservable> getValue(ref: U, property: KProperty<*>): T = value
    operator fun <U : BaseObservable> setValue(ref: U, property: KProperty<*>, newValue: T) {
        value = newValue
        ref.notifyPropertyChanged(tag)
    }
}


fun <T : Any> bindable(value: T, tag: Int): Bindable<T> = Bindable(value, tag)

@BindingAdapter("image")
fun changeImage(imageView: ImageView, imageHolder: ImageHolder?) {
    imageHolder?.let {
        when (it) {
            is ImageHolder.Empty -> imageView.setImageBitmap(null)
            is ImageHolder.ImageUri -> imageView.loadImage(it.uri)
            is ImageHolder.ImageDrawable -> imageView.setImageResource(it.id)
            is ImageHolder.ImageColor -> imageView.setBackgroundColor(it.color)
            is ImageHolder.ImageUriLoading -> imageView.loadImageLoading(it.uri)
        }
    }
}

@BindingAdapter("color")
fun changeBackgroundColor(cardView: MaterialCardView, color: Int) {
    cardView.setCardBackgroundColor(color)
}

fun ImageView.loadImage(uri: Uri) {
    Glide.with(context)
        .load(uri)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun ImageView.loadImageLoading(uri: Uri) {
    Glide.with(context)
        .load(uri)
        .placeholder(R.drawable.loading)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}