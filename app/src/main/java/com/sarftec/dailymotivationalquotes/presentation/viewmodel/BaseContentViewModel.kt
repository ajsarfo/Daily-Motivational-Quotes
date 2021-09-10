package com.sarftec.dailymotivationalquotes.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import com.sarftec.dailymotivationalquotes.presentation.CONTENT_MODEL_AUTHOR
import com.sarftec.dailymotivationalquotes.presentation.CONTENT_MODEL_CATEGORY
import com.sarftec.dailymotivationalquotes.presentation.CONTENT_MODEL_TYPE
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel
import kotlinx.coroutines.launch

abstract class BaseContentViewModel (
    protected val repository: ApplicationRepository
) : ViewModel() {

    protected var contentBundle: Bundle? = null

    abstract suspend fun fetch()

    fun setBundle(bundle: Bundle?) {
        if(contentBundle != null) return
        contentBundle = bundle
    }

    fun save(model: ContentModel) {
        if(model !is ContentModel.ListContentModel) return
        viewModelScope.launch {
            contentBundle?.run {
                when (getInt(CONTENT_MODEL_TYPE)) {
                    CONTENT_MODEL_AUTHOR -> repository.updateAuthorQuote(
                        model.id,
                        model.isFavorite
                    )
                    CONTENT_MODEL_CATEGORY -> repository.updateCategoryQuote(
                        model.id,
                        model.isFavorite
                    )
                }
            }
        }
    }
}