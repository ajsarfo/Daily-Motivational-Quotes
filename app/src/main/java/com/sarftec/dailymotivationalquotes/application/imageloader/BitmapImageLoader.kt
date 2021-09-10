package com.sarftec.dailymotivationalquotes.application.imageloader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitmapImageLoader @Inject constructor(
    @ApplicationContext private val context: Context,
    private val bitmapCache: BitmapCache
) {
   // private val imageLoader = ImageLoader(context)
    private val imageLoadingScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /*
    private suspend fun buildAndLoad(uri: Uri): Bitmap {
        return imageLoader.execute(
            ImageRequest.Builder(context)
                .data(uri)
                .build()
        ).drawable.let { (it as BitmapDrawable).bitmap }
    }
    */

    @SuppressLint("CheckResult")
    private fun glideLoadImage(uri: Uri, stateFlow: MutableStateFlow<Bitmap?>? = null) {
       Glide.with(context)
            .asBitmap()
            .load(uri)
           .into(
               object: CustomTarget<Bitmap>() {
                   override fun onResourceReady(
                       resource: Bitmap,
                       transition: Transition<in Bitmap>?
                   ) {
                       stateFlow?.value = resource
                       bitmapCache.saveBitmap(uri.toString(), resource)
                   }

                   override fun onLoadCleared(placeholder: Drawable?) {}
               }
           )
    }

    fun loadImageAsync(uri: Uri): StateFlow<Bitmap?> {
        return bitmapCache.getBitmap(uri.toString())?.let {
            MutableStateFlow<Bitmap?>(it)
        } ?: MutableStateFlow<Bitmap?>(null).also { stateFlow ->
            bitmapCache.getBitmap(uri.toString())?.let {
                stateFlow.value = it
            } ?: kotlin.run {
                glideLoadImage(uri, stateFlow)
            }
        }


       /*
       return bitmapCache.getBitmap(uri.toString())?.let {
            MutableStateFlow<Bitmap?>(it)
        } ?: MutableStateFlow<Bitmap?>(null).also { stateflow ->
           imageLoadingScope.launch {
               bitmapCache.getBitmap(uri.toString())?.let {
                   stateflow.value = it
               } ?: kotlin.run {
                   try {
                       buildAndLoad(uri).let {
                           stateflow.value = it
                           bitmapCache.saveBitmap(uri.toString(), it)
                       }
                   }
                   catch (e: Exception) {
                       //Log.v("TAG", "Cannot load $uri \nreason: $e")
                   }
               }
           }
       }
        */
    }

    fun destroy() {
        imageLoadingScope.coroutineContext.cancelChildren()
    }


    fun saveImage(uri: Uri, bitmap: Bitmap) {
        bitmapCache.saveBitmap(uri.toString(), bitmap)
    }

    fun preloadImages(uris: List<Uri>) {
        uris.forEach {
           glideLoadImage(it)
        }
       /*
        withContext(Dispatchers.IO) {
            uris.map { it.toString() to async { glideLoadImage(it) } }
        }.map { (key, deferred) ->
            key to deferred.await()
        }.forEach { (key, bitmap) ->
            bitmapCache.saveBitmap(key, bitmap)
        }
        */
    }
}