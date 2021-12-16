package com.sarftec.dailymotivationalquotes.application

import android.content.Context
import com.sarftec.dailymotivationalquotes.application.imagestore.ImageStore
import kotlinx.coroutines.CoroutineScope

class Dependency(
    val context: Context,
    val coroutineScope: CoroutineScope,
    val imageStore: ImageStore
)