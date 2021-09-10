package com.sarftec.dailymotivationalquotes.application.model.author

import com.sarftec.dailymotivationalquotes.domain.model.author.AuthorQuote

class AppAuthorQuote(
    val id: Int,
    val authorId: Int,
    val message: String,
    var isFavorite: Boolean
) {
    companion object {
        fun AuthorQuote.convert(authorId: Int) : AppAuthorQuote {
            return AppAuthorQuote(id, authorId, message, false)
        }
    }
}