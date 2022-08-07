@file:OptIn(ExperimentalMaterial3Api::class)

package com.sats.spikes.notifications

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.core.view.WindowCompat
import com.sats.spikes.notifications.ui.theme.SatsNotificationsSpikeTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlinx.parcelize.Parcelize

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
      Button(onClick = { context.showGxReminderNotification(sampleGxDetails) }) {
        Text("Spawn notification")
      }

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Button(onClick = { context.openNotificationSettings() }) {
          Text("Open notification settings")
        }
      }
    }
  }
}

fun LocalDateTime.format(): String {
  return format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))
}

@Parcelize
data class GxDetails(
  val id: String,
  val name: String,
  val nextSessionDateTime: LocalDateTime,
) : Parcelable

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

@RequiresApi(Build.VERSION_CODES.O)
fun Context.openNotificationSettings() {
  val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  }

  startActivity(intent)
}
