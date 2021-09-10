package com.sarftec.dailymotivationalquotes.presentation.model

import com.appodeal.ads.native_ad.views.NativeAdViewContentStream

sealed class ContentModel(
    val id: Int
) {
    class ListContentModel(
        id: Int,
        val title: String,
        val subtitle: String,
        var isFavorite: Boolean,
        var hasNext: Boolean = false,
        var hasPrevious: Boolean = false,
        val isAuthorQuote: Boolean = false
    ) : ContentModel(id)

    class SeparatorContentModel(
        id: Int,
        val nativeAd: NativeAdViewContentStream
    ) : ContentModel(id)
}