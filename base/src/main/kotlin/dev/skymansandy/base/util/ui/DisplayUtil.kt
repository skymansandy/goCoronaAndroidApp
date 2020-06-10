package dev.skymansandy.base.util.ui

import android.app.Activity
import android.util.DisplayMetrics

object DisplayUtil {

    private fun getWindowHeight(context: Activity): Int {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

}