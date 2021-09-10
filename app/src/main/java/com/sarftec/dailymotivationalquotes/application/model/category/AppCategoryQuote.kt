package com.sarftec.dailymotivationalquotes.application.model.category

import com.sarftec.dailymotivationalquotes.domain.model.category.CategoryQuote

class AppCategoryQuote(
    val id: Int,
    val categoryId: Int,
    val author: String,
    val message: String,
    val isFavorite: Boolean
) {
    companion object {
        fun CategoryQuote.convert(categoryId: Int) : AppCategoryQuote {
            return AppCategoryQuote(id, categoryId, author, message, false)
        }
    }
}