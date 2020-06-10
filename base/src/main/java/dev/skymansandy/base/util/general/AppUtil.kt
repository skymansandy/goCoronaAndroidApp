package dev.skymansandy.base.util.general

import android.Manifest.permission.CALL_PHONE
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.RequiresPermission
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

    fun sendEmail(
        context: Context,
        emailAddress: String,
        subject: String = "",
        body: String = "",
        chooserTitle: String = context.getString(R.string.send_via)
    ) {
        context.startActivity(
            Intent.createChooser(
                Intent(
                    Intent.ACTION_SENDTO,
                    Uri.fromParts("mailto", emailAddress, null)
                ).apply {
                    putExtra(Intent.EXTRA_EMAIL, emailAddress)
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, body)
                }, chooserTitle
            )
        )
    }

    fun sendSms(
        context: Context,
        mobileNum: String,
        body: String = "",
        chooserTitle: String = context.getString(R.string.send_via)
    ) {
        context.startActivity(
            Intent.createChooser(
                Intent(
                    Intent.ACTION_SENDTO,
                    Uri.fromParts("smsto", mobileNum, null)
                ).apply {
                    putExtra(Intent.EXTRA_TEXT, body)
                }, chooserTitle
            )
        )
    }
}