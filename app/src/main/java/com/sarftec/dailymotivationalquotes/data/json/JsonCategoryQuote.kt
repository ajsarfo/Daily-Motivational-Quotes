package com.sarftec.dailymotivationalquotes.data.json

import kotlinx.serialization.Serializable

@Serializable
class JsonCategoryQuote(
    val message: String,
    val name: String
)