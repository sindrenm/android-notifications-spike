package com.sats.spikes.notifications

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.sats.spikes.notifications.ui.theme.SatsNotificationsSpikeTheme

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
}
