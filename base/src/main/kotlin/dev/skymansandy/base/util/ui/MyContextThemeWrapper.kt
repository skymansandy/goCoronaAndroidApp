package dev.skymansandy.base.util.ui

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import java.util.*

object MyContextThemeWrapper {

    fun wrap(context: Context, localeString: String): ContextWrapper {
        val res = context.resources
        val configuration = res.configuration
        val locale = Locale(localeString)
        val newContext = when {
            Build.VERSION.SDK_INT > Build.VERSION_CODES.N -> {
                configuration.setLocale(locale)
                val localeList = LocaleList(locale)
                configuration.setLocales(localeList)
                context.createConfigurationContext(configuration)
            }
            else -> {
                configuration.setLocale(locale)
                context.createConfigurationContext(configuration)
            }
        }
        return ContextWrapper(newContext)
    }


}