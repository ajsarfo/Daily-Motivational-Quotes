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

class FavoriteBinding(
    private val dependency: Dependency,
    private val imageUri: Uri
) : BaseObservable() {

    @get:Bindable
    var image: ImageHolder by bindable(ImageHolder.Empty, BR.image)

    fun init() {
        dependency.apply {
            coroutineScope.launch {
                imageLoader.loadImageAsync(imageUri).collect { bitmap ->
                    bitmap?.let {
                        image = ImageHolder.ImageBitmap(it)
                        throw CancellationException()
                    }
                }
            }
        }
    }
}