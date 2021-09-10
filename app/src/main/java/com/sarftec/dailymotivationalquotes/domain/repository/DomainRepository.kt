package com.sarftec.dailymotivationalquotes.domain.repository

import com.sarftec.dailymotivationalquotes.OneOf
import com.sarftec.dailymotivationalquotes.domain.model.author.Author
import com.sarftec.dailymotivationalquotes.domain.model.author.AuthorQuote
import com.sarftec.dailymotivationalquotes.domain.model.category.Category
import com.sarftec.dailymotivationalquotes.domain.model.category.CategoryQuote

interface DomainRepository {
    suspend fun authors(): OneOf<List<Author>, Exception>
    suspend fun authorQuotes(authorId: Int): OneOf<List<AuthorQuote>, Exception>
    suspend fun categories(): OneOf<List<Category>, Exception>
    suspend fun categoryQuotes(categoryId: Int): OneOf<List<CategoryQuote>, Exception>
}