package com.sarftec.dailymotivationalquotes.domain.model.author

data class AuthorQuote(
    val id: Int,
    val authorId: Int,
    val message: String
)