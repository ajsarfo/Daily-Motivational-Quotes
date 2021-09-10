package com.sarftec.dailymotivationalquotes.presentation.binding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sarftec.dailymotivationalquotes.BR
import com.sarftec.dailymotivationalquotes.application.Dependency
import com.sarftec.dailymotivationalquotes.application.imageloader.ImageHolder
import com.sarftec.dailymotivationalquotes.application.imagestore.toAssetUri
import com.sarftec.dailymotivationalquotes.presentation.bindable
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainBinding(
    private val dependency: Dependency,
    val onFavorite: () -> Unit,
    val onRate: () -> Unit
) : BaseObservable() {

    @get:Bindable
    var backgroundImage: ImageHolder by bindable(ImageHolder.Empty, BR.backgroundImage)

    fun init() {
        val favoriteImageUri = "cool_ratings.png".toAssetUri("icon_images")
        dependency.coroutineScope.launch {
            dependency.imageLoader.loadImageAsync(favoriteImageUri).collect { bitmap ->
                bitmap?.let {
                    backgroundImage = ImageHolder.ImageBitmap(it)
                    throw CancellationException()
                }
            }
        }
    }
}