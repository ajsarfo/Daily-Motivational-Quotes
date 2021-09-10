package com.sarftec.dailymotivationalquotes.presentation.binding

import android.net.Uri
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sarftec.dailymotivationalquotes.BR
import com.sarftec.dailymotivationalquotes.application.Dependency
import com.sarftec.dailymotivationalquotes.application.imageloader.ImageHolder
import com.sarftec.dailymotivationalquotes.presentation.bindable
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        with(dependency) {
           coroutineScope.launch {
               imageLoader.loadImageAsync(Uri.parse(imageStringUri)).collect { bitmap ->
                   bitmap?.let {
                       image = ImageHolder.ImageBitmap(it)
                       throw CancellationException()
                   }
               }
           }
        }
    }
}