package com.sarftec.dailymotivationalquotes.presentation.model

sealed class MainModel(
    val id: Int,
    val name: String,
    val quoteSize: Int,
    var favoriteSize: Int
) {
    class AuthorMainModel(id: Int, name: String, size: Int, favoriteSize: Int) : MainModel(id, name, size, favoriteSize)
    class CategoryMainModel(id: Int, name: String, size: Int, favoriteSize: Int) : MainModel(id, name, size, favoriteSize)
}