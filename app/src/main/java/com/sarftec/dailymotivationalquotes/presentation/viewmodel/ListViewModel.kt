package com.sarftec.dailymotivationalquotes.presentation.viewmodel

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.sarftec.dailymotivationalquotes.application.manager.NativeManager
import com.sarftec.dailymotivationalquotes.application.manager.NetworkManager
import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import com.sarftec.dailymotivationalquotes.presentation.*
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel
import com.sarftec.dailymotivationalquotes.presentation.tools.ListWindowCreator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: ApplicationRepository,
    private val networkManager: NetworkManager
) : ViewModel() {

    private var separatorIdGenerator: Int = -1
    private var nativeManager: NativeManager? = null

    private var contentModels: List<ContentModel.ListContentModel>? = null

    private var contentBundle: Bundle? = null

    suspend fun fetch(): List<ContentModel.ListContentModel>? {
        return contentModels ?: fetchModels()?.shuffled()?.also {
            contentModels = it
        }
    }

    private suspend fun fetchModels(): List<ContentModel.ListContentModel>? {
        return contentBundle?.run {
            when (getInt(CONTENT_MODEL_TYPE)) {
                CONTENT_MODEL_AUTHOR -> getString(CONTENT_MODEL_NAME)?.let { name ->
                    repository.authorQuotes(getInt(CONTENT_MODEL_ID)).map {
                        ContentModel.ListContentModel(it.id, it.message, name, it.isFavorite)
                    }
                }
                else -> repository.categoryQuotes(getInt(CONTENT_MODEL_ID)).map {
                    ContentModel.ListContentModel(it.id, it.author, it.message, it.isFavorite)
                }
            }
        }
    }

    fun setBundle(bundle: Bundle?) {
        if (contentBundle != null) return
        contentBundle = bundle
    }

    fun getToolbarTitle() : String? {
        return contentBundle?.getString(CONTENT_MODEL_NAME)
    }

    fun save(model: ContentModel) {
        if (model !is ContentModel.ListContentModel) return
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

    suspend fun getPagingFlow(): Flow<PagingData<ContentModel>>? {
        return fetch()?.let {
            Pager(PagingConfig(10)) {
                ListModelSource(ListWindowCreator(it, 10).createWindowedList())
            }.flow
                .map { pagingData ->
                    pagingData.insertSeparators { first: ContentModel?, second: ContentModel? ->
                            if (first == null || second == null || nativeManager == null) null
                            else if(first is ContentModel.SeparatorContentModel && second is ContentModel.SeparatorContentModel) null
                            else {
                                nativeManager!!.getNativeAd()?.let {
                                    ContentModel.SeparatorContentModel(separatorIdGenerator--, it)
                                }
                            }
                        }
                }.cachedIn(viewModelScope)
        }
    }

    fun createNativeManager(activity: Activity) {
        nativeManager = NativeManager(activity, networkManager).apply {
            preloadAds()
        }
    }

    fun destroyNativeManager() {
        nativeManager?.destroy()
    }

    private class ListModelSource(
        private val windowedModels: List<List<ContentModel>>
    ) : PagingSource<Int, ContentModel>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ContentModel> {
            val key = params.key ?: 0
            return LoadResult.Page(
                prevKey = if (key == 0) null else key - 1,
                nextKey = if (key == windowedModels.size - 1) null else key + 1,
                data = windowedModels[key]
            )
        }

        override fun getRefreshKey(state: PagingState<Int, ContentModel>): Int? {
            return state.anchorPosition
        }
    }
}