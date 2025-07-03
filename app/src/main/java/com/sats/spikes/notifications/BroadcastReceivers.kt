package com.sats.spikes.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class BookGxReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    runBlocking {
      val gxDetails = intent.extractGxDetails()

      context.showBookingGxNotification(gxDetails)

      delay(3.seconds)

      context.showBookedGxNotification(gxDetails)
    }
  }
}

class UnbookGxReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    runBlocking {
      val gxDetails = intent.extractGxDetails()

      context.showUnbookingGxNotification(gxDetails)

      delay(3.seconds)

      context.showUnbookedGxNotification(gxDetails)
    }
  }
}

private fun Intent.extractGxDetails(): GxDetails {
  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    extractGxDetailsApi33()
  } else {
    extractGxDetailsPreApi33()
  }
}

@RequiresApi(33)
private fun Intent.extractGxDetailsApi33(): GxDetails {
  return requireNotNull(getParcelableExtra("gx-details", GxDetails::class.java)) {
    "Required GxDetails “gx-details” was absent from the Intent."
  }
}

@Suppress("DEPRECATION")
private fun Intent.extractGxDetailsPreApi33(): GxDetails {
  return requireNotNull(getParcelableExtra("gx-details")) {
    "Required GxDetails “gx-details” was absent from the Intent."
  }
}
