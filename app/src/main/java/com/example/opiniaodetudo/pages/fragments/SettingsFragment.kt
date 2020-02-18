package com.example.opiniaodetudo.pages.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.pages.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {
    companion object {
        const val NIGHT_MODE_PREF = "night_mode_pref"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferencescreen, rootKey)
        configNightMode()
    }

    private fun configNightMode() {
        val preference = preferenceManager.findPreference(NIGHT_MODE_PREF)
        preference.setOnPreferenceChangeListener { preference, newValue ->
            (activity as MainActivity).setNightMode()
            true
        }
    }
}
