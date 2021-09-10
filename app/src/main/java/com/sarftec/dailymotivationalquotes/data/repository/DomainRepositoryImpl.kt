package com.sarftec.dailymotivationalquotes.data.repository

import android.content.Context
import com.sarftec.dailymotivationalquotes.OneOf
import com.sarftec.dailymotivationalquotes.data.AUTHOR_QUOTES_FOLDER
import com.sarftec.dailymotivationalquotes.data.CATEGORY_QUOTES_FOLDER
import com.sarftec.dailymotivationalquotes.data.json.JsonAuthorQuote
import com.sarftec.dailymotivationalquotes.data.json.JsonCategoryContent
import com.sarftec.dailymotivationalquotes.domain.model.author.Author
import com.sarftec.dailymotivationalquotes.domain.model.author.AuthorQuote
import com.sarftec.dailymotivationalquotes.domain.model.category.Category
import com.sarftec.dailymotivationalquotes.domain.model.category.CategoryQuote
import com.sarftec.dailymotivationalquotes.domain.repository.DomainRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*
import javax.inject.Inject


class DomainRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DomainRepository {

    private val authorList = context.assets.list(AUTHOR_QUOTES_FOLDER)!!
        .asList()
        .sortedBy { it.lowercase(Locale.ENGLISH) }

    private val categoryList = context.assets.list(CATEGORY_QUOTES_FOLDER)!!
        .asList()
        .sortedBy { it.lowercase(Locale.ENGLISH) }


    override suspend fun authors(): OneOf<List<Author>, Exception> {
        return OneOf.Success(
            authorList
                .map { it.substringBeforeLast(".json") }
                .mapIndexed { index, fileName -> Author(index, fileName) }
        )
    }

    override suspend fun authorQuotes(authorId: Int): OneOf<List<AuthorQuote>, Exception> {
        return context.assets.open("$AUTHOR_QUOTES_FOLDER/${authorList[authorId]}").use {
            val jsonAuthorQuotes: List<JsonAuthorQuote> = Json.decodeFromString(
                it.bufferedReader().readText()
            )
            jsonAuthorQuotes.mapIndexed { index, jsonAuthorQuote ->
                AuthorQuote(index, authorId, jsonAuthorQuote.text)
            }
        }.let {
            OneOf.success(it)
        }
    }

    override suspend fun categories(): OneOf<List<Category>, Exception> {
        return OneOf.Success(
            categoryList
                .map { it.substringBeforeLast(".json") }
                .mapIndexed { index, fileName -> Category(index, fileName) }
        )
    }

    override suspend fun categoryQuotes(categoryId: Int): OneOf<List<CategoryQuote>, Exception> {
        return context.assets.open("$CATEGORY_QUOTES_FOLDER/${categoryList[categoryId]}").use {
            val jsonCategoryContent: JsonCategoryContent = Json.decodeFromString(
                it.bufferedReader().readText()
            )
            jsonCategoryContent.quotes.mapIndexed { index, jsonCategoryQuote ->
                CategoryQuote(index, categoryId, jsonCategoryQuote.name, jsonCategoryQuote.message)
            }
        }.let {
            OneOf.success(it)
        }
    }
}