package dev.skymansandy.base.util.ui

import android.content.Context
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import dev.skymansandy.base.R
import dev.skymansandy.base.ui.base.BaseActivity
import java.util.*

object NightModeUtil {

    fun setNightMode(context: Context) {
        PreferenceManager.getDefaultSharedPreferences(context).getString(
            context.getString(R.string.pref_key_night),
            context.getString(R.string.pref_night_auto)
        )?.apply {
            val mode = NightModeType.valueOf(this.toUpperCase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(mode.value)
            if (context is BaseActivity<*, *, *, *>)
                context.recreate()
        }
    }
}

enum class NightModeType(val value: Int) {
    AUTO(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY),
    ON(AppCompatDelegate.MODE_NIGHT_YES),
    OFF(AppCompatDelegate.MODE_NIGHT_NO)
}