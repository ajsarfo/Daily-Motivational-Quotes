package com.sarftec.dailymotivationalquotes.application

import android.content.Context
import com.sarftec.dailymotivationalquotes.application.file.APP_CREATED
import com.sarftec.dailymotivationalquotes.application.file.editSettings
import com.sarftec.dailymotivationalquotes.application.file.readSettings
import com.sarftec.dailymotivationalquotes.application.model.author.AppAuthor.Companion.convert
import com.sarftec.dailymotivationalquotes.application.model.author.AppAuthorQuote.Companion.convert
import com.sarftec.dailymotivationalquotes.application.model.category.AppCategory.Companion.convert
import com.sarftec.dailymotivationalquotes.application.model.category.AppCategoryQuote.Companion.convert
import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import com.sarftec.dailymotivationalquotes.domain.usecase.FetchAuthorQuotes
import com.sarftec.dailymotivationalquotes.domain.usecase.FetchAuthors
import com.sarftec.dailymotivationalquotes.domain.usecase.FetchCategories
import com.sarftec.dailymotivationalquotes.domain.usecase.FetchCategoryQuotes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class Setup @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchAuthorsUseCase: FetchAuthors,
    private val fetchAuthorQuotesUseCase: FetchAuthorQuotes,
    private val fetchCategoriesUseCase: FetchCategories,
    private val fetchCategoryQuotesUseCase: FetchCategoryQuotes,
    private val applicationRepository: ApplicationRepository
) {

    suspend fun createDatabase() {
        insertAuthors()
        insertCategories()
        context.editSettings(APP_CREATED, true)
    }

    private suspend fun insertAuthors() {
        fetchAuthorsUseCase.execute { authors ->
            authors.forEach { author ->
                fetchAuthorQuotesUseCase.execute(author.id) { quotes ->
                    val authorId = applicationRepository.insertAuthor(author.convert(quotes.size))
                    applicationRepository.insertAuthorQuotes(
                        quotes.map {
                            it.convert(authorId.toInt())
                        }
                    )
                }
            }
        }
    }

    private suspend fun insertCategories() {
        fetchCategoriesUseCase.execute { categories ->
            categories.forEach { category ->
                fetchCategoryQuotesUseCase.execute(category.id) { quotes ->
                    val categoryId =
                        applicationRepository.insertCategory(category.convert(quotes.size))
                    applicationRepository.insertCategoryQuotes(
                        quotes.map {
                            it.convert(categoryId.toInt())
                        }
                    )
                }
            }
        }
    }

    suspend fun isDatabaseCreated(): Boolean {
        return context.readSettings(APP_CREATED, false).first()
    }
}