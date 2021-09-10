package com.sarftec.dailymotivationalquotes.domain.usecase

import com.sarftec.dailymotivationalquotes.domain.model.category.CategoryQuote
import com.sarftec.dailymotivationalquotes.domain.repository.DomainRepository
import javax.inject.Inject

class FetchCategoryQuotes @Inject constructor(
    private val repository: DomainRepository
) : UseCase<Int, List<CategoryQuote>>() {

    override suspend fun run(param: Int): List<CategoryQuote> {
        var result: List<CategoryQuote>? = null
        repository.categoryQuotes(param).onSuccess {
            result = it
        }
        return result ?: emptyList()
    }
}