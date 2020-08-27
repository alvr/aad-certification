package com.google.developers.lettervault.ui.setting

import android.os.Bundle
import android.preference.Preference
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.google.developers.lettervault.R
import com.google.developers.lettervault.util.NightMode
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference(getString(R.string.pref_key_night))?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                newValue as? String

                when (newValue) {
                    getString(R.string.pref_night_auto) -> updateTheme(NightMode.AUTO.value)
                    getString(R.string.pref_night_on) -> updateTheme(NightMode.ON.value)
                    getString(R.string.pref_night_off) -> updateTheme(NightMode.OFF.value)
                }

                true
            }
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

}
