package com.sarftec.dailymotivationalquotes.application.imageloader

import android.graphics.Bitmap

sealed class ImageHolder {
    object Empty : ImageHolder()
    class ImageBitmap(val image: Bitmap) : ImageHolder()
    class ImageDrawable(val icon: Int) : ImageHolder()
    class ImageColor(val color: Int) : ImageHolder()
}