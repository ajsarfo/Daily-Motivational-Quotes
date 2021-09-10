package com.sarftec.dailymotivationalquotes.presentation.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.sarftec.dailymotivationalquotes.R
import com.sarftec.dailymotivationalquotes.presentation.activity.SplashActivity
import com.sarftec.dailymotivationalquotes.receiver.ActionReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationMaker @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager by lazy {
        NotificationManagerCompat.from(context)
    }

    fun notify(quote: NotificationQuote) {
        notificationManager.notify(NOTIFICATION_ID, buildNotification(quote))
    }

    private fun getView(quote: NotificationQuote, layoutId: Int): RemoteViews {
        return RemoteViews(context.packageName, layoutId).apply {
            setTextViewText(R.id.message, quote.message)
            setTextViewText(R.id.name, quote.name)
        }
    }

    private fun buildNotification(quote: NotificationQuote): Notification {
        createNotificationChannel()
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.notification_icon)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(getView(quote, R.layout.notification_collapsed))
            .setCustomBigContentView(getView(quote, R.layout.notification_expanded))
            .setContentIntent(getPendingIntent())
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.color_primary))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(
                createAction(
                    R.drawable.notification_share,
                    "Share",
                    getActionIntent(quote, ActionReceiver.SHARE),
                    2
                )
            )
            .addAction(
                createAction(
                    R.drawable.notification_cancel,
                    "Dismiss",
                    getActionIntent(quote, ActionReceiver.DISMISS),
                    3
                )
            )
            .build()
    }

    private fun getActionIntent(quote: NotificationQuote, action: String): Intent {
        return Intent(context, ActionReceiver::class.java).apply {
            putExtra(ActionReceiver.ACTION, action)
            putExtra(ActionReceiver.NAME, quote.name)
            putExtra(ActionReceiver.MESSAGE, quote.message)
        }
    }

    private fun getPendingIntent() = PendingIntent.getActivity(
        context,
        1,
        Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        },
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    private fun createAction(
        @DrawableRes drawableId: Int,
        text: String,
        intent: Intent,
        requestCode: Int
    ): NotificationCompat.Action {
        return NotificationCompat.Action.Builder(
            drawableId,
            text,
            PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        ).build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                "Channel 1",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Daily Inspirational Quotes"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL = "notification_channel"
    }
}