package dev.skymansandy.gocorona.presentation.about.settings

import android.app.UiModeManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import dev.skymansandy.gocorona.R

class SettingsFragment : PreferenceFragmentCompat() {

    private var nightModeListPref: ListPreference? = null
    private var appLanguageListPref: ListPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nightModeListPref = findPreference(getString(R.string.pref_key_night)) as? ListPreference
        appLanguageListPref =
            findPreference(getString(R.string.pref_key_app_language)) as? ListPreference

        nightModeListPref?.setOnPreferenceChangeListener { _, newValue ->
            updateTheme(
                when (newValue) {
                    getString(R.string.pref_night_on) -> UiModeManager.MODE_NIGHT_YES
                    getString(R.string.pref_night_off) -> UiModeManager.MODE_NIGHT_NO
                    else -> UiModeManager.MODE_NIGHT_AUTO
                }
            )
        }

        appLanguageListPref?.setOnPreferenceChangeListener { _, newValue ->
            updateLanguage(newValue.toString())
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

    private fun updateLanguage(localeStr: String): Boolean {
        Toast.makeText(activity, localeStr, Toast.LENGTH_SHORT).show()
        return true
    }

}
