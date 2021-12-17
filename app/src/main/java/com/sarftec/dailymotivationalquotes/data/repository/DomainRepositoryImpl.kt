package com.sarftec.dailymotivationalquotes.data.repository

import android.content.Context
import com.sarftec.dailymotivationalquotes.OneOf
import com.sarftec.dailymotivationalquotes.data.AUTHOR_QUOTES_FOLDER
import com.sarftec.dailymotivationalquotes.data.CATEGORY_QUOTES_FOLDER
import com.sarftec.dailymotivationalquotes.domain.model.author.Author
import com.sarftec.dailymotivationalquotes.domain.model.author.AuthorQuote
import com.sarftec.dailymotivationalquotes.domain.model.category.Category
import com.sarftec.dailymotivationalquotes.domain.model.category.CategoryQuote
import com.sarftec.dailymotivationalquotes.domain.repository.DomainRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
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
        val parser = ParseAuthorQuotes(
            context.assets.open("$AUTHOR_QUOTES_FOLDER/${authorList[authorId]}")
        )
        return parser.parse().mapIndexed { index, parsedAuthorQuote ->
            AuthorQuote(index, authorId, parsedAuthorQuote.message)
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
        val parser = ParseCategoryQuotes(
            context.assets.open("$CATEGORY_QUOTES_FOLDER/${categoryList[categoryId]}")
        )
        return parser.parse().mapIndexed { index, parsedQuote ->
            CategoryQuote(index, categoryId, parsedQuote.name, parsedQuote.message)
        }.let {
            OneOf.success(it)
        }
    }

    class ParseCategoryQuotes(private val inputStream: InputStream) {
        private var collector = mutableListOf<String>()

        private val quotes = mutableListOf<ParsedCategoryQuote>()

        fun parse(): List<ParsedCategoryQuote> {
            inputStream.bufferedReader().use {
                it.forEachLine { line -> addLine(line) }
            }
            return quotes
        }

        private fun addLine(line: String) {
            collector.add(line)
            if (collector.size < 2) return
            quotes.add(ParsedCategoryQuote(collector[0], collector[1]))
            collector.clear()
        }

        class ParsedCategoryQuote(val message: String, val name: String)
    }

    class ParseAuthorQuotes(private val inputStream: InputStream) {

        private var parsedQuotes = mutableListOf<ParsedAuthorQuote>()

        fun parse() : List<ParsedAuthorQuote> {
            inputStream.bufferedReader().use {
                it.forEachLine { line -> addLine(line) }
            }
            return parsedQuotes
        }

        private fun addLine(line: String) {
            parsedQuotes.add(ParsedAuthorQuote(line))
        }

        class ParsedAuthorQuote(val message: String)
    }
}