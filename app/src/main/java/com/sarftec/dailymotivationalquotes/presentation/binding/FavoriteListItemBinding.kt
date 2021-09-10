package com.sarftec.dailymotivationalquotes.presentation.binding

import android.os.Bundle
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sarftec.dailymotivationalquotes.BR
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.application.file.copy
import com.sarftec.dailymotivationalquotes.application.file.share
import com.sarftec.dailymotivationalquotes.application.file.toast
import com.sarftec.dailymotivationalquotes.application.file.vibrate
import com.sarftec.dailymotivationalquotes.application.imageloader.ImageHolder
import com.sarftec.dailymotivationalquotes.presentation.activity.BaseActivity
import com.sarftec.dailymotivationalquotes.presentation.activity.FavoriteListActivity
import com.sarftec.dailymotivationalquotes.presentation.bindable
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.FavoriteViewModel

class FavoriteListItemBinding(
    private val activityContent: FavoriteListActivity.FavoriteActivityContent,
    private val favoriteViewModel: FavoriteViewModel,
    private val model: ContentModel.ListContentModel,
) : BaseObservable() {

    @get:Bindable
    var title by bindable(model.title, BR.title)

    @get:Bindable
    var subtitle by bindable(model.subtitle, BR.subtitle)

    @get:Bindable
    var favoriteDrawable: ImageHolder by bindable(getFavoriteDrawable(model), BR.favoriteDrawable)

    private fun getFavoriteDrawable(model: ContentModel.ListContentModel): ImageHolder.ImageDrawable {
        return if (model.isFavorite) ImageHolder.ImageDrawable(R.drawable.favorite_checked)
        else ImageHolder.ImageDrawable(R.drawable.favorite_unchecked_blue)
    }

    fun onFavorite() {
       activityContent.dependency.apply {
           coroutineScope.vibrate(context)
       }
        model.isFavorite = !model.isFavorite
        favoriteDrawable = getFavoriteDrawable(model)
        favoriteViewModel.save(model)
    }

    fun onCopy() {
        activityContent.dependency.apply {
            coroutineScope.vibrate(context)
        }
        with(activityContent.dependency.context) {
            copy("\"${model.title}\"\n\n_${model.subtitle}", "share")
            toast("Copied to clipboard")
        }
    }

    fun onShare() {
        activityContent.dependency.apply {
            coroutineScope.vibrate(context)
        }
        activityContent.dependency.context.share(
            "\"${model.title}\"\n\n_${model.subtitle}", "Share"
        )
    }

    fun onExpand() {
        activityContent.dependency.apply {
            coroutineScope.vibrate(context)
        }
        activityContent.navigate(
            Bundle().apply {
                putString(BaseActivity.FAVORITE_QUOTE_TITLE, model.title)
                putString(BaseActivity.FAVORITE_QUOTE_SUBTITLE, model.subtitle)
            }
        )
    }
}