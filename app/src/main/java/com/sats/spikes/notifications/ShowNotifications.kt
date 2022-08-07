package com.sats.spikes.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService

fun Context.showGxReminderNotification(gxDetails: GxDetails) {
  val openAppIntent = Intent(this, MainActivity::class.java)
  val openAppPendingIntent = PendingIntent.getActivity(this, 0, openAppIntent, IntentImportance)

  val bookGxPendingIntent = buildBookGxPendingIntent(gxDetails)

  val notification = notificationBuilder
    .setContentTitle("Book next session?")
    .setContentText("Hope you enjoined ${gxDetails.name}! Book for ${gxDetails.nextSessionDateTime.format()}, too?")
    .setContentIntent(openAppPendingIntent)
    .setAutoCancel(true)
    .addAction(R.drawable.ic_add, "Book now", bookGxPendingIntent)
    .build()

  val notificationManager = getSystemService<NotificationManager>()

  notificationManager?.notify(BookGxNotificationId, notification)
}

fun Context.showBookingGxNotification(gxDetails: GxDetails) {
  val notification = notificationBuilder
    .setContentTitle("Booking ${gxDetails.name} …")
    .setContentText("Booking ${gxDetails.name} at ${gxDetails.nextSessionDateTime.format()} …")
    .withProgressBar()
    .build()

  val notificationManager = getSystemService<NotificationManager>()

  notificationManager?.notify(BookGxNotificationId, notification)
}

fun Context.showBookedGxNotification(gxDetails: GxDetails) {
  val unbookGxPendingIntent = buildUnbookGxPendingIntent(gxDetails)

  val notification = notificationBuilder
    .setContentTitle("Booking successful")
    .setContentText("You have now booked ${gxDetails.name} at ${gxDetails.nextSessionDateTime.format()}.")
    .addAction(R.drawable.ic_remove, "Unbook", unbookGxPendingIntent)
    .build()

  val notificationManager = getSystemService<NotificationManager>()

  notificationManager?.notify(BookGxNotificationId, notification)
}

fun Context.showUnbookingGxNotification(gxDetails: GxDetails) {
  val notification = notificationBuilder
    .setContentTitle("Unbooking ${gxDetails.name} …")
    .setContentText("Unbooking ${gxDetails.name} at ${gxDetails.nextSessionDateTime.format()} …")
    .withProgressBar()
    .build()

  val notificationManager = getSystemService<NotificationManager>()

  notificationManager?.notify(BookGxNotificationId, notification)
}

fun Context.showUnbookedGxNotification(gxDetails: GxDetails) {
  val bookGxPendingIntent = buildBookGxPendingIntent(gxDetails)

  val notification = notificationBuilder
    .setContentTitle("Unbooked ${gxDetails.name}")
    .setContentText("${gxDetails.name} at ${gxDetails.nextSessionDateTime.format()} unbooked. Sure you want that, though?")
    .addAction(R.drawable.ic_add, "Nah, book again", bookGxPendingIntent)
    .build()

  val notificationManager = getSystemService<NotificationManager>()

  notificationManager?.notify(BookGxNotificationId, notification)
}

private fun Context.buildBookGxPendingIntent(gxDetails: GxDetails): PendingIntent {
    val bookGxIntent = Intent(this, BookGxReceiver::class.java).withGxDetails(gxDetails)

    return PendingIntent.getBroadcast(this, BookUnbookRequestCode, bookGxIntent, IntentImportance)
  }

private fun Context.buildUnbookGxPendingIntent(gxDetails: GxDetails): PendingIntent {
  val unbookGxIntent = Intent(this, UnbookGxReceiver::class.java).withGxDetails(gxDetails)

  return PendingIntent.getBroadcast(this, BookUnbookRequestCode, unbookGxIntent, IntentImportance)
}

private fun Intent.withGxDetails(gxDetails: GxDetails): Intent {
  return this.apply { putExtra("gx-details", gxDetails) }
}

private val Context.notificationBuilder: NotificationCompat.Builder
  get() = NotificationCompat.Builder(this, "rebook-gx-reminders")
    .setSmallIcon(R.drawable.ic_gym)
    .setPriority(NotificationCompat.PRIORITY_DEFAULT) // for pre-Oreo, where channels aren't available.

private fun NotificationCompat.Builder.withProgressBar(): NotificationCompat.Builder {
  return this
    .setProgress(100, 50, true) // we don't know the progress status, only that it's ongoing
    .setOngoing(true) // don't allow swiping away in-progress booking/unbooking
}

private const val BookUnbookRequestCode = 1
private const val IntentImportance = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
private const val BookGxNotificationId = 0
