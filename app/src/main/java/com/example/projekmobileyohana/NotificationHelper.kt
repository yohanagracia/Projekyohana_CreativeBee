package com.example.projekmobileyohana

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object NotificationHelper {

    private const val CHANNEL_ID = "creative_bee_channel_high"
    private const val NOTIFICATION_ID = 1001

    // Membuat saluran notifikasi
    fun createNotificationChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notifikasi Creative Bee",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description =
                    "Informasi dan pemberitahuan Creative Bee"
            }

            val manager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }

    // Menampilkan notifikasi
    fun showNotification(context: Context) {

        // Memeriksa izin notifikasi Android 13 ke atas
        if (
            Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // Membuat isi notifikasi
        val notification = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Creative Bee by Carla")
            .setContentText("Ada informasi terbaru untuk kamu!")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "Jangan lewatkan informasi terbaru mengenai produk, " +
                                "kelas, dan kegiatan workshop clay Creative Bee."
                    )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .build()

        // Mengirim notifikasi ke sistem Android
        NotificationManagerCompat.from(context)
            .notify(NOTIFICATION_ID, notification)
    }
}
