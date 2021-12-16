package com.sarftec.dailymotivationalquotes.presentation.binding

import android.net.Uri
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sarftec.dailymotivationalquotes.BR
import com.sarftec.dailymotivationalquotes.application.Dependency
import com.sarftec.dailymotivationalquotes.application.imagestore.ImageHolder
import com.sarftec.dailymotivationalquotes.presentation.bindable

class FavoriteBinding(
    private val dependency: Dependency,
    private val imageUri: Uri
) : BaseObservable() {

    @get:Bindable
    var image: ImageHolder by bindable(ImageHolder.Empty, BR.image)

    fun init() {
        dependency.apply {
            image = ImageHolder.ImageUri(imageUri)
        }
    }
}