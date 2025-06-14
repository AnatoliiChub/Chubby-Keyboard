package com.chubbykeyboard.service.haptic

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val SHORT_VIBRATION_DURATION = 10L // milliseconds
const val LONG_VIBRATION_DURATION = 30L // milliseconds

class ChubbyHapticManager @Inject constructor(@ApplicationContext val context: Context) : HapticManager {

    private val vibrator = context.getSystemService(Vibrator::class.java) as Vibrator

    override fun keyEvent() {
        defaultEvent(SHORT_VIBRATION_DURATION)
    }

    override fun functionalEvent() {
        defaultEvent(LONG_VIBRATION_DURATION)
    }

    private fun defaultEvent(duration: Long) {
        VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE).also { effect ->
            vibrator.vibrate(effect)
        }
    }
}
