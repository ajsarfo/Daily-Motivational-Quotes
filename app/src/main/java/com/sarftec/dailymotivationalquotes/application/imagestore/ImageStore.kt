package com.sarftec.dailymotivationalquotes.application.imagestore

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageStore @Inject constructor(
    @ApplicationContext context: Context
) {

    private val backgroundImages: Set<String> = context
        .assets
        .list(BACKGROUND_IMAGE_FOLDER)!!
        .map { it.lowercase(Locale.ENGLISH) }
        .toHashSet()

    private val authorImages: Set<String> = context
        .assets
        .list(AUTHOR_IMAGE_FOLDER)!!
        .map { it.lowercase(Locale.ENGLISH) }
        .toHashSet()

    fun authorImageUris(): List<Uri> = authorImages.map { it.toAssetUri(AUTHOR_IMAGE_FOLDER) }

    fun backgroundImageUris(): List<Uri> =
        backgroundImages.map { it.toAssetUri(BACKGROUND_IMAGE_FOLDER) }

    fun authorImage(name: String): Uri {
        val resolvedImage = "${name.lowercase(Locale.ENGLISH)}.jpg"
        val result = resolvedImage.takeIf { authorImages.contains(resolvedImage) } ?: "default.jpg"
        return result.toAssetUri(AUTHOR_IMAGE_FOLDER)
    }

    fun iconImageUris(context: Context): List<Uri> {
        return context.assets
            .list(ICON_IMAGE_FOLDER)!!
            .map { it.lowercase(Locale.ENGLISH) }
            .toHashSet()
            .map { it.toAssetUri(ICON_IMAGE_FOLDER) }
    }

    fun iconImage(name: String): Uri {
        return name.toAssetUri(ICON_IMAGE_FOLDER)
    }

    fun randomBackgroundImage(): Uri {
        return backgroundImages.random().toAssetUri(BACKGROUND_IMAGE_FOLDER)
    }
}
