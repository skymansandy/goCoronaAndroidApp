package dev.skymansandy.base.util.ui

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBarUtil {

    fun showShortSnack(snackBarView: View, message: String) {
        Snackbar.make(snackBarView, message, Snackbar.LENGTH_SHORT).show()
    }

    fun showLongSnack(snackBarView: View, message: String) {
        Snackbar.make(snackBarView, message, Snackbar.LENGTH_LONG).show()
    }
}