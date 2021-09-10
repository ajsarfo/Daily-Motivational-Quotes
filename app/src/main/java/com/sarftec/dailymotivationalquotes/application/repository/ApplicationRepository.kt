package com.sarftec.dailymotivationalquotes.application.repository

import com.sarftec.dailymotivationalquotes.application.model.author.AppAuthor
import com.sarftec.dailymotivationalquotes.application.model.author.AppAuthorQuote
import com.sarftec.dailymotivationalquotes.application.model.category.AppCategory
import com.sarftec.dailymotivationalquotes.application.model.category.AppCategoryQuote

interface ApplicationRepository {
    suspend fun insertAuthor(author: AppAuthor) : Long
    suspend fun insertAuthorQuotes(quotes: List<AppAuthorQuote>)

    suspend fun authors() : List<AppAuthor>
    suspend fun author(id: Int) : AppAuthor
    suspend fun authorQuotes() : List<AppAuthorQuote>
    suspend fun authorQuotes(authorId: Int) : List<AppAuthorQuote>

    suspend fun updateAuthorQuote(id: Int, isFavorite: Boolean)

    suspend fun favoriteAuthorQuotes() : List<AppAuthorQuote>
    suspend fun favoriteAuthorQuotes(authorId: Int) : List<AppAuthorQuote>


    suspend fun insertCategory(category: AppCategory) : Long
    suspend fun insertCategoryQuotes(quotes: List<AppCategoryQuote>)

    suspend fun categories() : List<AppCategory>
    suspend fun category(id: Int) : AppCategory
    suspend fun categoryQuotes() : List<AppCategoryQuote>
    suspend fun categoryQuotes(categoryId: Int) : List<AppCategoryQuote>

    suspend fun updateCategoryQuote(id: Int, isFavorite: Boolean)

    suspend fun favoriteCategoryQuotes() : List<AppCategoryQuote>
    suspend fun favoriteCategoryQuotes(categoryId: Int) : List<AppCategoryQuote>
}