package com.sarftec.dailymotivationalquotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import com.sarftec.dailymotivationalquotes.presentation.model.MainModel
import kotlinx.coroutines.launch

abstract class BaseListViewModel(
    protected val repository: ApplicationRepository
) : ViewModel() {

    private val _models = MutableLiveData<List<MainModel>>()
    val models: LiveData<List<MainModel>>
        get() = _models

    private var favoriteUpdatableListener: FavoriteUpdatable? = null
    private var selectedModel: MainModel? = null

    protected abstract suspend fun fetchModels(): List<MainModel>

    fun fetch() {
        viewModelScope.launch {
            _models.value = fetchModels()!!
        }
    }

    fun onResume() {
        viewModelScope.launch {
            selectedModel?.let {
                val size = when(it) {
                    is MainModel.AuthorMainModel -> repository.favoriteAuthorQuotes(it.id).size
                    else -> repository.favoriteCategoryQuotes(it.id).size
                }
                favoriteUpdatableListener?.updateFavorite(size)
                favoriteUpdatableListener = null
            }
        }
    }

    fun setSelectedModel(model: MainModel) {
        selectedModel = model
    }

    fun setFavoriteUpdatable(favoriteUpdatable: FavoriteUpdatable) {
       favoriteUpdatableListener = favoriteUpdatable
    }

    interface FavoriteUpdatable {
        fun updateFavorite(size: Int)
    }
}