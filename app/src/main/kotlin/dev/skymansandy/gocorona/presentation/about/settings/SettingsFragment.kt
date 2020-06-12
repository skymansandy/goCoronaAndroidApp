package dev.skymansandy.gocorona.presentation.about.settings

import android.app.UiModeManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import dev.skymansandy.gocorona.R

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var nightModeListPref: ListPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nightModeListPref = findPreference(getString(R.string.pref_key_night)) as ListPreference
        nightModeListPref.setOnPreferenceChangeListener { _, newValue ->
            updateTheme(
                when (newValue) {
                    getString(R.string.pref_night_on) -> UiModeManager.MODE_NIGHT_YES
                    getString(R.string.pref_night_off) -> UiModeManager.MODE_NIGHT_NO
                    else -> UiModeManager.MODE_NIGHT_AUTO
                }
            )
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

}
