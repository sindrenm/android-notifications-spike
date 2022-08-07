@file:OptIn(ExperimentalMaterial3Api::class)

package com.sats.spikes.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.core.view.WindowCompat
import com.sats.spikes.notifications.ui.theme.SatsNotificationsSpikeTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      SatsNotificationsSpikeTheme {
        App()
      }
    }
  }
}

@Composable
fun App() {
  val context = LocalContext.current

  Scaffold(
    modifier = Modifier.fillMaxSize()
  ) { screenPadding ->
    Column(
      modifier = Modifier
        .padding(screenPadding)
        .fillMaxSize()
        .wrapContentSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      Button(onClick = { showNotification(context, sampleGxDetails) }) {
        Text("Spawn notification")
      }

      Button(onClick = {}) {
        Text("Open notification settings")
      }
    }
  }
}

fun showNotification(context: Context, gxDetails: GxDetails) {
  val openAppIntent = Intent(context, MainActivity::class.java)
  val openAppPendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, PendingIntent.FLAG_IMMUTABLE)

  val notification = NotificationCompat.Builder(context, "rebook-gx-reminders")
    .setSmallIcon(R.drawable.ic_gym)
    .setContentTitle("Book next session?")
    .setContentText("Hope you enjoined ${gxDetails.name}! Book for ${gxDetails.nextSessionDateTime.format()}?")
    .setPriority(NotificationCompat.PRIORITY_DEFAULT) // for pre-Oreo, where channels aren't available.
    .setContentIntent(openAppPendingIntent)
    .setAutoCancel(true)
    .build()

  val notificationManager = context.getSystemService<NotificationManager>()

  notificationManager?.notify(0, notification)
}

fun LocalDateTime.format(): String {
  return format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))
}

data class GxDetails(
  val id: String,
  val name: String,
  val nextSessionDateTime: LocalDateTime,
)

private val sampleGxDetails: GxDetails
  get() = GxDetails(
    id = "hiit-and-run",
    name = "HIIT And Run",
    nextSessionDateTime = LocalDateTime.now()
      .plusWeeks(1)
      .minusHours(1)
      .withMinute(0)
      .withSecond(0),
  )
