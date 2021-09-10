package com.sarftec.dailymotivationalquotes.presentation.binding

import android.net.Uri
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sarftec.dailymotivationalquotes.BR
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.application.file.copy
import com.sarftec.dailymotivationalquotes.application.file.share
import com.sarftec.dailymotivationalquotes.application.file.toast
import com.sarftec.dailymotivationalquotes.application.file.vibrate
import com.sarftec.dailymotivationalquotes.application.imageloader.ImageHolder
import com.sarftec.dailymotivationalquotes.presentation.bindable
import com.sarftec.dailymotivationalquotes.presentation.fragment.BaseFragment
import com.sarftec.dailymotivationalquotes.presentation.listener.ContentListener
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseContentBinding(
    protected var model: ContentModel.ListContentModel,
    protected val dependency: BaseFragment.FragmentDependency,
    protected val contentListener: ContentListener
) : BaseObservable() {

    @get:Bindable
    var title by bindable(model.title, BR.title)

    @get:Bindable
    var subtitle by bindable("~${model.subtitle}", BR.subtitle)

    @get:Bindable
    var favoriteDrawable: ImageHolder by bindable(getFavoriteDrawable(model), BR.favoriteDrawable)

    @get:Bindable
    var backgroundImage: ImageHolder by bindable(ImageHolder.Empty, BR.backgroundImage)

    protected fun getFavoriteDrawable(model: ContentModel.ListContentModel): ImageHolder.ImageDrawable {
        return if (model.isFavorite) ImageHolder.ImageDrawable(R.drawable.favorite_checked)
        else ImageHolder.ImageDrawable(R.drawable.favorite_unchecked)
    }

    protected fun changeImage(uri: Uri) = dependency.apply {
        coroutineScope.launch {
            imageLoader.loadImageAsync(uri).collect { bitmap ->
                bitmap?.let {
                    backgroundImage = ImageHolder.ImageBitmap(it)
                    throw CancellationException()
                }
            }
        }
    }

    fun onCopy() {
        dependency.apply {
            coroutineScope.vibrate(context)
        }
        with(dependency.context) {
            copy("\"${model.title}\"\n\n_${model.subtitle}", "share")
            toast("Copied to clipboard")
        }
    }

    fun onShare() {
        dependency.apply {
            coroutineScope.vibrate(context)
            context.share("\"${model.title}\"\n\n_${model.subtitle}", "Share")
        }
    }

    fun onFavorite() {
        dependency.apply {
            coroutineScope.vibrate(context)
        }
        model.isFavorite = !model.isFavorite
        favoriteDrawable = getFavoriteDrawable(model)
        contentListener.save(model)
    }
}