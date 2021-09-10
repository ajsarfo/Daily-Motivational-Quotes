package com.sarftec.dailymotivationalquotes.application.model.author

import com.sarftec.dailymotivationalquotes.domain.model.author.Author

class AppAuthor(
    val id: Int,
    val name: String,
    val quoteSize: Int
) {

    companion object {
        fun Author.convert(quoteSize: Int): AppAuthor {
            return AppAuthor(id, name, quoteSize)
        }
    }
}