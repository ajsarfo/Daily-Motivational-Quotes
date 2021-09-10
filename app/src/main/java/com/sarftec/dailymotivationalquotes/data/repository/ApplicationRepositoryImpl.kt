package com.sarftec.dailymotivationalquotes.data.repository

import android.content.Context
import androidx.room.Room
import com.sarftec.dailymotivationalquotes.application.model.author.AppAuthor
import com.sarftec.dailymotivationalquotes.application.model.author.AppAuthorQuote
import com.sarftec.dailymotivationalquotes.application.model.category.AppCategory
import com.sarftec.dailymotivationalquotes.application.model.category.AppCategoryQuote
import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import com.sarftec.dailymotivationalquotes.data.database.Database
import com.sarftec.dailymotivationalquotes.data.database.entity.author.AppAuthorEntity
import com.sarftec.dailymotivationalquotes.data.database.entity.author.AppAuthorQuoteEntity
import com.sarftec.dailymotivationalquotes.data.database.entity.catgeory.AppCategoryEntity
import com.sarftec.dailymotivationalquotes.data.database.entity.catgeory.AppCategoryQuoteEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApplicationRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
) : ApplicationRepository {

    private val database: Database = Room.databaseBuilder(
        context,
        Database::class.java,
        "app_database"
    ).build()

    override suspend fun insertAuthor(author: AppAuthor): Long {
        return author.run {
            database.authorDao().insert(
                AppAuthorEntity(
                    name = name,
                    quoteSize = quoteSize
                )
            )
        }
    }

    override suspend fun insertAuthorQuotes(quotes: List<AppAuthorQuote>) {
        quotes.map {
            AppAuthorQuoteEntity(
                authorId = it.authorId,
                message = it.message
            )
        }.let {
            database.authorQuoteDao().insert(it)
        }
    }

    override suspend fun authors(): List<AppAuthor> {
        return database.authorDao().authors().map {
            AppAuthor(it.id, it.name, it.quoteSize)
        }
    }

    override suspend fun author(id: Int): AppAuthor {
        return database.authorDao().author(id).let {
            AppAuthor(it.id, it.name, it.quoteSize)
        }
    }

    override suspend fun authorQuotes(): List<AppAuthorQuote> {
        return database.authorQuoteDao().quotes().map {
            AppAuthorQuote(it.id, it.authorId, it.message, it.isFavorite)
        }
    }

    override suspend fun authorQuotes(authorId: Int): List<AppAuthorQuote> {
        return database.authorQuoteDao().quotes(authorId).map {
            AppAuthorQuote(it.id, it.authorId, it.message, it.isFavorite)
        }
    }

    override suspend fun updateAuthorQuote(id: Int, isFavorite: Boolean) {
        database.authorQuoteDao().update(id, isFavorite)
    }

    override suspend fun favoriteAuthorQuotes(): List<AppAuthorQuote> {
        return database.authorQuoteDao().favorites().map {
            AppAuthorQuote(it.id, it.authorId, it.message, it.isFavorite)
        }
    }

    override suspend fun favoriteAuthorQuotes(authorId: Int): List<AppAuthorQuote> {
        return database.authorQuoteDao().favorites(authorId).map {
            AppAuthorQuote(it.id, it.authorId, it.message, it.isFavorite)
        }
    }

    override suspend fun insertCategory(category: AppCategory): Long {
        return category.run {
            database.categoryDao().insert(
                AppCategoryEntity(
                    name = name,
                    quoteSize = quoteSize
                )
            )
        }
    }

    override suspend fun insertCategoryQuotes(quotes: List<AppCategoryQuote>) {
        database.categoryQuoteDao().insert(
            quotes.map {
                AppCategoryQuoteEntity(
                    categoryId = it.categoryId,
                    message = it.message,
                    author = it.author
                )
            }
        )
    }

    override suspend fun categories(): List<AppCategory> {
        return database.categoryDao().categories().map {
            AppCategory(it.id, it.name, it.quoteSize)
        }
    }

    override suspend fun category(id: Int): AppCategory {
        return database.categoryDao().category(id).let {
            AppCategory(it.id, it.name, it.quoteSize)
        }
    }

    override suspend fun categoryQuotes(): List<AppCategoryQuote> {
        return database.categoryQuoteDao().quotes().map {
            AppCategoryQuote(it.id, it.categoryId, it.message, it.author, it.isFavorite)
        }
    }

    override suspend fun categoryQuotes(categoryId: Int): List<AppCategoryQuote> {
        return database.categoryQuoteDao().quotes(categoryId).map {
            AppCategoryQuote(it.id, it.categoryId, it.message, it.author, it.isFavorite)
        }
    }

    override suspend fun updateCategoryQuote(id: Int, isFavorite: Boolean) {
            database.categoryQuoteDao().update(id, isFavorite)
    }

    override suspend fun favoriteCategoryQuotes(): List<AppCategoryQuote> {
        return database.categoryQuoteDao().favorites().map {
            AppCategoryQuote(it.id, it.categoryId, it.message, it.author, it.isFavorite)
        }
    }

    override suspend fun favoriteCategoryQuotes(categoryId: Int): List<AppCategoryQuote> {
        return database.categoryQuoteDao().favorites(categoryId).map {
            AppCategoryQuote(it.id, it.categoryId, it.message, it.author, it.isFavorite)
        }
    }
}