package com.sarftec.dailymotivationalquotes.presentation.binding

import com.sarftec.dailymotivationalquotes.application.file.vibrate
import com.sarftec.dailymotivationalquotes.presentation.fragment.BaseFragment
import com.sarftec.dailymotivationalquotes.presentation.listener.ContentListener
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.SingleViewModel

class SingleContentBinding(
   model: ContentModel.ListContentModel,
   dependency: BaseFragment.FragmentDependency,
   contentListener: ContentListener,
   private val singleViewModel: SingleViewModel
) : BaseContentBinding(model, dependency, contentListener) {

    fun init() {
        favoriteDrawable = getFavoriteDrawable(model)
        changeImage(dependency.imageStore.randomBackgroundImage())
    }


    fun next() {
        dependency.apply {
            coroutineScope.vibrate(context)
        }
        singleViewModel.next()
    }

    fun previous() {
        dependency.apply {
            coroutineScope.vibrate(context)
        }
        singleViewModel.previous()
    }

    fun changeModel(model: ContentModel.ListContentModel) {
        changeImage(dependency.imageStore.randomBackgroundImage())
        this.model = model
        title = model.title
        subtitle = "~${model.subtitle}"
        favoriteDrawable = getFavoriteDrawable(model)
    }
}