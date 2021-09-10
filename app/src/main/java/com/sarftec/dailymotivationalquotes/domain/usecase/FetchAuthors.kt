package com.sarftec.dailymotivationalquotes.domain.usecase

import com.sarftec.dailymotivationalquotes.domain.model.author.Author
import com.sarftec.dailymotivationalquotes.domain.repository.DomainRepository
import javax.inject.Inject

class FetchAuthors @Inject constructor(
    private val repository: DomainRepository
) : UseCase<Unit, List<Author>>() {

    suspend fun execute(callback: suspend (List<Author>) -> Unit) {
        execute(Unit, callback)
    }

    override suspend fun run(param: Unit): List<Author> {
        var authors: List<Author>? = null
        repository.authors().onSuccess {
            authors = it
        }
        return authors ?: emptyList()
    }
}