package dev.skymansandy.base.util.general

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

object DeviceUtil {

    private const val NOT_AVAILABLE = "N/A"

    @SuppressLint("HardwareIds")
    fun getAndroidID(context: Context): String {
        return if (context.contentResolver != null)
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        else NOT_AVAILABLE
    }
}