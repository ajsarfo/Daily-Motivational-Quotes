package com.sarftec.dailymotivationalquotes.domain.usecase

import com.sarftec.dailymotivationalquotes.domain.model.category.Category
import com.sarftec.dailymotivationalquotes.domain.repository.DomainRepository
import javax.inject.Inject

class FetchCategories @Inject constructor(
    private val repository: DomainRepository
) : UseCase<Unit, List<Category>>() {

    suspend fun execute(callback: suspend (List<Category>) -> Unit) {
        execute(Unit, callback)
    }

    override suspend fun run(param: Unit): List<Category> {
        var result: List<Category>? = null
            repository.categories().onSuccess {
                result = it
            }
        return result ?: emptyList()
    }
}