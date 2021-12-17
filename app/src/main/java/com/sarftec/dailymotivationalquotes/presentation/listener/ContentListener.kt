package com.sarftec.dailymotivationalquotes.presentation.listener

import androidx.paging.PagingData
import com.sarftec.dailymotivationalquotes.presentation.model.ContentModel
import com.sarftec.dailymotivationalquotes.presentation.viewmodel.ListViewModel
import kotlinx.coroutines.flow.Flow


interface ContentListener {
    suspend fun getContentModels() : List<ContentModel.ListContentModel>?
    suspend fun getContentModelPagingFlow() : Flow<PagingData<ContentModel>>?
    fun getListViewModel() : ListViewModel
    fun save(contentModel: ContentModel)
    fun showRewardVideo(callback: () -> Unit)
}