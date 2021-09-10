package com.sarftec.dailymotivationalquotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel
import com.sarftec.dailymotivationalquotes.presentation.tools.CustomListIterator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleViewModel @Inject constructor() : ViewModel() {

    private val _model = MutableLiveData<ContentModel.ListContentModel>()
    val model: LiveData<ContentModel.ListContentModel>
        get() = _model

    private var iterator: CustomListIterator<ContentModel.ListContentModel>? = null

    fun next() = iterator?.run {
        if (hasNext()) _model.value = next().also {
            it.hasNext = hasNext()
            it.hasPrevious = hasPrevious()
        }
    }

    fun previous() = iterator?.run {
        if (hasPrevious()) _model.value = previous().also {
            it.hasNext = hasNext()
            it.hasPrevious = hasPrevious()
        }
    }

    fun setModels(models: List<ContentModel.ListContentModel>) {
        iterator = CustomListIterator(models)
    }
}