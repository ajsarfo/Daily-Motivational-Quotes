package com.sarftec.dailymotivationalquotes.presentation.manager

import android.content.Context
import com.sarftec.dailymotivationalquotes.application.file.*
import com.sarftec.dailymotivationalquotes.presentation.tools.RatingsDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RatingsManager(
    private val coroutineScope: CoroutineScope,
    private val ratingsDialog: RatingsDialog
) {

    private val ratingsInterval = 4

    fun init() {
        coroutineScope.launch {
            ratingsDialog.context.let { context ->
                if (!context.readSettings(SHOW_RATINGS, true).first()) return@let
                val startCount = context.readSettings(APP_START_COUNT, 1).first()
                if (startCount >= 3) {
                    setup(ratingsDialog.context)
                    ratingsDialog.show()
                } else context.editSettings(APP_START_COUNT, startCount + 1)
            }
        }
    }

    private fun setup(context: Context) {
        ratingsDialog.onReview = {
            context.rateApp()
            coroutineScope.launch {
                context.editSettings(SHOW_RATINGS, false)
            }
        }

        ratingsDialog.onNextTime = {
            coroutineScope.launch {
                val startCount = context.readSettings(APP_START_COUNT, 1).first()
                context.editSettings(APP_START_COUNT, startCount.minus(ratingsInterval))
            }
        }
    }
}