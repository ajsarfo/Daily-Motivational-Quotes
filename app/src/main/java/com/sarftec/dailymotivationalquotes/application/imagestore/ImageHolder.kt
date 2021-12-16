package com.sarftec.dailymotivationalquotes.application.imagestore

import android.net.Uri
import androidx.annotation.DrawableRes

sealed class ImageHolder {
    class ImageDrawable(@DrawableRes val id: Int) : ImageHolder()
    class ImageUri(val uri: Uri) : ImageHolder()
    class ImageColor(val color: Int) : ImageHolder()
    class ImageUriLoading(val uri: Uri) : ImageHolder()
    object Empty : ImageHolder()
}