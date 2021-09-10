package com.sarftec.dailymotivationalquotes.application.injection

import android.graphics.Bitmap
import android.util.LruCache
import com.sarftec.dailymotivationalquotes.application.imageloader.BitmapCache
import com.sarftec.dailymotivationalquotes.application.imageloader.BitmapCacheImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StaticModule {
    @Singleton
    @Provides
    fun lruBitmapCache(): LruCache<String, Bitmap> {
        val cacheSize = Runtime.getRuntime().maxMemory() / 1024 / 8
        return object : LruCache<String, Bitmap>(cacheSize.toInt()) {
            override fun sizeOf(key: String?, value: Bitmap): Int {
                return value.byteCount / 1024
            }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModule {
    @Singleton
    @Binds
    abstract fun appBitmapCache(bitmapCache: BitmapCacheImpl) : BitmapCache
}