package com.example.holaa_trendy_wear_os.presentation.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DealNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("deal_title") ?: return
        val description = intent.getStringExtra("deal_description") ?: return
        val discount = intent.getStringExtra("deal_discount") ?: return

        NotificationUtils(context).showNotification(title, description, discount)
    }
}