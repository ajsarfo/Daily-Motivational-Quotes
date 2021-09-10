package com.sarftec.dailymotivationalquotes.application.imageloader

import android.graphics.Bitmap

interface BitmapCache {
    fun getBitmap(key: String) : Bitmap?
    fun saveBitmap(key: String, bitmap: Bitmap)
}