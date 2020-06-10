package dev.skymansandy.base.util.ui

import android.content.Context
import android.widget.Toast

object ToastUtil {
    fun showShortToast(applicationContext: Context?, message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(applicationContext: Context?, message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}