package com.sarftec.dailymotivationalquotes.presentation.viewmodel

import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import com.sarftec.dailymotivationalquotes.presentation.model.MainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(
    repository: ApplicationRepository
) : BaseListViewModel(repository) {

    override suspend fun fetchModels(): List<MainModel> {
        return repository.authors()
            .sortedBy { it.name }
            .map {
                MainModel.AuthorMainModel(it.id,
                    it.name,
                    it.quoteSize,
                    repository.favoriteAuthorQuotes(it.id).size
                )
            }
    }
}