package com.sarftec.dailymotivationalquotes.application.imagestore

import android.net.Uri

const val BACKGROUND_IMAGE_FOLDER = "background_images"
const val AUTHOR_IMAGE_FOLDER = "author_images"
const val ICON_IMAGE_FOLDER = "icons"

fun String.toAssetUri(folder: String) : Uri {
    return Uri.parse("file:///android_asset/$folder/$this")
}