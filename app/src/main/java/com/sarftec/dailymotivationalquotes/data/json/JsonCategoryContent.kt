package com.sarftec.dailymotivationalquotes.data.json

import kotlinx.serialization.Serializable

@Serializable
class JsonCategoryContent(
    val title: String,
    val quotes: List<JsonCategoryQuote>
)