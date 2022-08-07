package com.sats.spikes.notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.getSystemService

class NotificationsSpikeApp : Application() {
  override fun onCreate() {
    super.onCreate()

    setupNotificationChannels()
  }

  private fun setupNotificationChannels() {
    // Channels are supported from Oreo and upwards. Short-circuit on lower versions.
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

    val channel = NotificationChannel(
      /* id = */ "rebook-gx-reminders",
      /* name = */ "Re-book GX Reminders",
      /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
    )

    channel.description = "Reminders to re-book a group class you just attended."

    val notificationManager = getSystemService<NotificationManager>()

    notificationManager?.createNotificationChannel(channel)
  }
}
