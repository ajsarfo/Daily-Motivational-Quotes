package com.sarftec.dailymotivationalquotes.domain.usecase

import com.sarftec.dailymotivationalquotes.domain.model.author.AuthorQuote
import com.sarftec.dailymotivationalquotes.domain.repository.DomainRepository
import javax.inject.Inject

class FetchAuthorQuotes @Inject constructor(
    private val repository: DomainRepository
) : UseCase<Int, List<AuthorQuote>>() {

    override suspend fun run(param: Int): List<AuthorQuote> {
        var authorQuotes: List<AuthorQuote>? = null
        repository.authorQuotes(param).onSuccess {
            authorQuotes = it
        }
        return authorQuotes ?: emptyList()
    }
}