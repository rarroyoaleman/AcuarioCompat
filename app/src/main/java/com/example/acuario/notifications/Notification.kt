package com.example.acuario.notifications

/**
 * return notificationId is a unique int for each notification that you must define
 */
 fun getNotificationId(): Int {
     return 9
}

enum class Notification(val channel: String) {
    CHANNEL_ID("1"),

}