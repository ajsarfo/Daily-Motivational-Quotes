package com.sarftec.dailymotivationalquotes.application.model.category

import com.sarftec.dailymotivationalquotes.domain.model.category.Category

class AppCategory(
    val id: Int,
    val name: String,
    val quoteSize: Int
) {
    companion object {
        fun Category.convert(quoteSize: Int) : AppCategory {
            return AppCategory(id, name, quoteSize)
        }
    }
}