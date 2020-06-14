package dev.skymansandy.base.util.general

import android.Manifest.permission.CALL_PHONE
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.browser.customtabs.CustomTabsIntent
import dev.skymansandy.base.R


object AppUtil {

    @RequiresPermission(CALL_PHONE)
    fun dialNumber(context: Context, phoneNum: String) {
        if (PermissionUtil.isPermissionGranted(context, CALL_PHONE)) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phoneNum")
            context.startActivity(callIntent)
        } else {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$phoneNum")
            context.startActivity(callIntent)
        }
    }

    fun launchUrl(context: Context?, url: String) {
        context?.let {
            try {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(browserIntent)
            } catch (t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    context.getString(R.string.something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun launchUrlInChromeTab(context: Context?, url: String, toolbarColor: Int = 0) {
        context?.let {
            try {
                val builder = CustomTabsIntent.Builder()
                if (toolbarColor != 0)
                    builder.setToolbarColor(toolbarColor)
                val customTabIntent = builder.build()
                customTabIntent.launchUrl(it, Uri.parse(url))
            } catch (t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    context.getString(R.string.something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun launchInChromeOrDefault(context: Context?, url: String, toolbarColor: Int = 0) {
        try {
            launchUrlInChromeTab(context, url, toolbarColor)
        } catch (e: Exception) {
            launchUrl(context, url)
        }
    }
}