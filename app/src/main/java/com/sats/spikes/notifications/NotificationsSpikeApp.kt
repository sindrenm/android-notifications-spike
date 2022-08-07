package com.sats.spikes.notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
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

    val gxGroup = NotificationChannelGroup(
      /* id = */ "gx",
      /* name = */ "Group exercises"
    )

    val gxRemindersChannel = NotificationChannel(
      /* id = */ "rebook-gx-reminders",
      /* name = */ "Re-book GX Reminders",
      /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
      group = gxGroup.id
      description = "Reminders to re-book a group class you just attended."
    }

    val gxJoinsChannel = NotificationChannel(
      /* id = */ "gx-joins",
      /* name = */ "Joins",
      /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
      group = gxGroup.id
      description = "When someone you follow joins a class you have booked."
    }

    val gxCancelsChannel = NotificationChannel(
      /* id = */ "gx-cancels",
      /* name = */ "Cancels",
      /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
      group = gxGroup.id
      description = "When someone cancels a class you both had booked."
    }

    val gxInvitationsChannel = NotificationChannel(
      /* id = */ "gx-invitations",
      /* name = */ "Invitations to class",
      /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
      group = gxGroup.id
      description = "Invitations to join a group class"
    }

    val socialGroup = NotificationChannelGroup(
      /* id = */ "social",
      /* name = */ "Social"
    )

    val likesChannel = NotificationChannel(
      /* id = */ "social-likes",
      /* name = */ "Likes",
      /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
      description = "Someone likes your workout."
      group = socialGroup.id
    }

    val commentsChannel = NotificationChannel(
      /* id = */ "social-comments",
      /* name = */ "Comments",
      /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
      description = "Comments on your activity/"
      group = socialGroup.id
    }

    val commentsOnOthersChannel = NotificationChannel(
      /* id = */ "social-comments-on-others",
      /* name = */ "Comment on others activity",
      /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
      description = "Comments on activity that you have commented on."
      group = socialGroup.id
    }

    val notificationManager = getSystemService<NotificationManager>()

    notificationManager?.createNotificationChannelGroup(gxGroup)
    notificationManager?.createNotificationChannel(gxRemindersChannel)
    notificationManager?.createNotificationChannel(gxJoinsChannel)
    notificationManager?.createNotificationChannel(gxCancelsChannel)
    notificationManager?.createNotificationChannel(gxInvitationsChannel)

    notificationManager?.createNotificationChannelGroup(socialGroup)
    notificationManager?.createNotificationChannel(likesChannel)
    notificationManager?.createNotificationChannel(commentsChannel)
    notificationManager?.createNotificationChannel(commentsOnOthersChannel)
  }
}
