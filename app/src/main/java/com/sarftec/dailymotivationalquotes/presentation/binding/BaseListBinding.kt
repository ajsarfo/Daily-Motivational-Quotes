package com.sarftec.dailymotivationalquotes.presentation.binding

import android.os.Bundle
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sarftec.dailymotivationalquotes.BR
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.application.file.vibrate
import com.sarftec.dailymotivationalquotes.application.imagestore.ImageHolder
import com.sarftec.dailymotivationalquotes.presentation.*
import com.sarftec.dailymotivationalquotes.presentation.adapter.MainBaseListAdapter
import com.sarftec.dailymotivationalquotes.presentation.fragment.BaseFragment
import com.sarftec.dailymotivationalquotes.presentation.model.MainModel
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.BaseListViewModel

class BaseListBinding(
    private val dependency: BaseFragment.FragmentDependency,
    private val viewModel: BaseListViewModel,
    private val cache: MainBaseListAdapter.InitialColorCache,
    private val position: Int,
    val mainModel: MainModel
) : BaseObservable(), BaseListViewModel.FavoriteUpdatable {

    @get:Bindable
    var image: ImageHolder by bindable(ImageHolder.Empty, BR.image)

    @get:Bindable
    var showInitial: Int by bindable(View.GONE, BR.showInitial)

    @get:Bindable
    val initial by bindable(cache.getInitial(mainModel.name), BR.initial)

    @get:Bindable
    var favoriteSize by bindable(mainModel.favoriteSize.toString(), BR.favoriteSize)

    @get:Bindable
    var showFavoriteSize by bindable(favoriteSizeVisibility(), BR.showFavoriteSize)

    private fun setAuthorBackground(name: String) {
        dependency.apply {
            this@BaseListBinding.image = ImageHolder.ImageUriLoading(
                imageStore.authorImage(name)
            )
        }
    }

    private fun favoriteSizeVisibility(): Int {
        return if (mainModel.favoriteSize > 0) View.VISIBLE else View.GONE
    }

    private fun setCategoryBackground() {
        image = ImageHolder.ImageColor(cache.getColor(position))
    }

    fun init() {
        if (mainModel is MainModel.CategoryMainModel) {
            showInitial = View.VISIBLE
            setCategoryBackground()
        } else {
            showInitial = View.GONE
            //image = ImageHolder.ImageDrawable(R.drawable.loading)
            setAuthorBackground(mainModel.name)
        }

    }

    fun onClick() {
        dependency.coroutineScope.vibrate(dependency.context)
        val type = if (mainModel is MainModel.AuthorMainModel) CONTENT_MODEL_AUTHOR
        else CONTENT_MODEL_CATEGORY
        viewModel.setSelectedModel(mainModel)
        viewModel.setFavoriteUpdatable(this)
        dependency.fragmentListener.navigate(
            Bundle().apply {
                putInt(CONTENT_MODEL_TYPE, type)
                putInt(CONTENT_MODEL_ID, mainModel.id)
                putString(CONTENT_MODEL_NAME, mainModel.name)
            }
        )
    }

    override fun updateFavorite(size: Int) {
        favoriteSize = size.toString()
        mainModel.favoriteSize = size
        showFavoriteSize = favoriteSizeVisibility()
    }
}