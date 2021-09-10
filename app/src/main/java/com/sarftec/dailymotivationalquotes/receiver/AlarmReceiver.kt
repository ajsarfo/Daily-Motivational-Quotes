package com.sarftec.dailymotivationalquotes.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sarftec.dailymotivationalquotes.presentation.notification.NotificationMaker
import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import com.sarftec.dailymotivationalquotes.presentation.notification.NotificationQuote
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: ApplicationRepository

    @Inject
    lateinit var notificationMaker: NotificationMaker

    override fun onReceive(context: Context, intent: Intent) {
        runBlocking {
            repository.authorQuotes()
                .shuffled()
                .firstOrNull() {
                    it.message.length in 100..250
                }
                ?.let {
                    notificationMaker.notify(
                        NotificationQuote(
                            it.message,
                            repository.author(it.authorId).name
                        )
                    )
                }
        }
    }
}
