package com.sarftec.dailymotivationalquotes.domain.model.category

data class CategoryQuote(
    val id: Int,
    val categoryId: Int,
    val author: String,
    val message: String
)