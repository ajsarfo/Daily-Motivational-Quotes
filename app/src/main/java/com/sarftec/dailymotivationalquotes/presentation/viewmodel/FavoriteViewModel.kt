package com.sarftec.dailymotivationalquotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: ApplicationRepository
) : ViewModel() {

    private val _models = MutableLiveData<List<ContentModel.ListContentModel>>()
    val model: LiveData<List<ContentModel.ListContentModel>>
        get() = _models

    fun fetch() {
        viewModelScope.launch {
            val favorites: MutableList<ContentModel.ListContentModel> = mutableListOf()
            favorites.addAll(
                repository.favoriteAuthorQuotes().map {
                    ContentModel.ListContentModel(
                        it.id,
                        it.message,
                        repository.author(it.authorId).name,
                        it.isFavorite,
                        isAuthorQuote = true
                    )
                }
            )
            favorites.addAll(
                repository.favoriteCategoryQuotes().map {
                    ContentModel.ListContentModel(
                        it.id,
                        it.author,
                        repository.category(it.categoryId).name,
                        it.isFavorite,
                        isAuthorQuote = false
                    )
                }
            )
            _models.value = favorites
        }
    }

    fun save(model: ContentModel.ListContentModel) {
        viewModelScope.launch {
            if(model.isAuthorQuote) {
                repository.updateAuthorQuote(model.id, model.isFavorite)
            }
            else repository.updateCategoryQuote(model.id, model.isFavorite)
        }
    }
}