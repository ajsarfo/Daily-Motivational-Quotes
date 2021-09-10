package com.sarftec.dailymotivationalquotes.data.json

import kotlinx.serialization.Serializable

@Serializable
class JsonAuthorQuote(
    val text: String,
    val author: String
)