package com.sats.spikes.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
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
  return requireNotNull(getParcelableExtra("gx-details")) {
    "Required GxDetails “gx-details” was absent from the Intent."
  }
}
