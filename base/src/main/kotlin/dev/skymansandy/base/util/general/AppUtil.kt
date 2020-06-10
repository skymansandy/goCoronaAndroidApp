package dev.skymansandy.base.util.general

import android.Manifest.permission.CALL_PHONE
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.RequiresPermission


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
}