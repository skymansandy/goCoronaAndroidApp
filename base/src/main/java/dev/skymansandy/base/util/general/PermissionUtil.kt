package dev.skymansandy.base.util.general

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionUtil {

    fun arePermissionsGranted(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (!isPermissionGranted(context, permission)) {
                return false
            }
        }
        return true
    }

    fun isPermissionGranted(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

}
