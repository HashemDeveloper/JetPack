package com.api.jetpack.utils

interface INotificationService {
    val CHANEL_ID: String get() = "dogs_chanel_id"
    val NOTIFICATION_ID: Int get() = 123
    fun createNotification()

}