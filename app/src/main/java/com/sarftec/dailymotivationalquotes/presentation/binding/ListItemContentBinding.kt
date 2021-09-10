package com.sarftec.dailymotivationalquotes.presentation.binding

import com.sarftec.dailymotivationalquotes.application.file.vibrate
import com.sarftec.dailymotivationalquotes.presentation.adapter.ViewListItemAdapter
import com.sarftec.dailymotivationalquotes.presentation.fragment.BaseFragment
import com.sarftec.dailymotivationalquotes.presentation.listener.ContentListener
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel

class ListItemContentBinding(
    dependency: BaseFragment.FragmentDependency,
    contentListener: ContentListener,
    private val imageCache: ViewListItemAdapter.ImageCache,
    private val position: Int,
    model: ContentModel.ListContentModel
) : BaseContentBinding(model, dependency, contentListener) {

    fun init() {
        favoriteDrawable = getFavoriteDrawable(model)
        changeImage(imageCache.get(position))
    }

    fun onChangeImage() {
        dependency.apply {
            coroutineScope.vibrate(context)
        }
       changeImage(
           dependency.imageStore.randomBackgroundImage().also {
               imageCache.replace(position, it)
           }
       )
    }
}