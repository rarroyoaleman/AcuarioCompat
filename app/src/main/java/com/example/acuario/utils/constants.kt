package com.example.acuario.utils

class constants {

    companion object {
        /* FIREBASE */
        private const val TAG = "MainActivity"
        const val NOTIFICATION_REQUEST_CODE = 1234


        /**
         * return notificationId is a unique int for each notification that you must define
         */
        fun getNotificationId(): Int {
            return 9
        }

        enum class Notification(val channel: String) {
            CHANNEL_ID("1"),

        }

        /* END FIREBASE */

    }

}