package com.api.jetpack.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.api.jetpack.DogActivity
import com.api.jetpack.R
import javax.inject.Inject

class NotificationService @Inject constructor(): INotificationService {
    @Inject
    lateinit var context: Context

    override fun createNotification() {
        createNotificationChannel()
        val intent = Intent(this.context, DogActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this.context, 0, intent, 0)
        val notificationIcon = BitmapFactory.decodeResource(this.context.resources, R.drawable.dog_icon)
        val notification = NotificationCompat.Builder(this.context, CHANEL_ID)
            .setSmallIcon(R.drawable.dog_icon_color)
            .setLargeIcon(notificationIcon)
            .setContentTitle("Dogs Retrieved")
            .setContentText("New dogs arrived check it out")
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(notificationIcon)
                .bigLargeIcon(null))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        NotificationManagerCompat.from(this.context).notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANEL_ID
            val descriptionText = "Chanel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val chanel = NotificationChannel(CHANEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = this.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(chanel)
        }
    }
}