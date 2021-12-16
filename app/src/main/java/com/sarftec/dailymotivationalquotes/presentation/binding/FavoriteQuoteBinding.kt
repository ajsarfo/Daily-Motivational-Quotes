package com.sarftec.dailymotivationalquotes.presentation.binding

import android.net.Uri
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sarftec.dailymotivationalquotes.BR
import com.sarftec.dailymotivationalquotes.application.Dependency
import com.sarftec.dailymotivationalquotes.application.imagestore.ImageHolder
import com.sarftec.dailymotivationalquotes.presentation.bindable

class FavoriteQuoteBinding(
    private val dependency: Dependency,
    val title: String,
    subtitle: String,
    private val imageStringUri: String
) : BaseObservable() {

    @get:Bindable
    var image: ImageHolder by bindable(ImageHolder.Empty, BR.image)

    @get:Bindable
    var subtitle by bindable("~$subtitle", BR.subtitle)

    fun init() {
        image = ImageHolder.ImageUri(
            Uri.parse(imageStringUri)
        )
    }
}