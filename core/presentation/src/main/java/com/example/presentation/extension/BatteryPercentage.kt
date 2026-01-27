package com.example.presentation.extension

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

fun Context.getBatteryPercentage(): Float? {

    val batteryStatus: Intent? = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    return batteryStatus?.let { intent ->
        val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        level * 100 / scale.toFloat()
    }
}

fun Context.getBatteryStatus(): Int? {

    val batteryStatus: Intent? = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    return batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
}