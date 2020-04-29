package com.example.opiniaodetudo.pages.fragments

import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.pages.AboutActivity
import com.example.opiniaodetudo.pages.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {
    companion object {
        const val NIGHT_MODE_PREF = "night_mode_pref"
        const val ABOUT = "about"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferencescreen, rootKey)
        configNightMode()
        configAbout()
    }

    private fun configNightMode() {
        val preference = preferenceManager.findPreference(NIGHT_MODE_PREF)
        preference.setOnPreferenceChangeListener { preference, newValue ->
            (activity as MainActivity).setNightMode()
            true
        }
    }

    private fun configAbout() {
        val preference = preferenceManager.findPreference(ABOUT)
        preference.setOnPreferenceClickListener {
            val intent = Intent(activity!!, AboutActivity::class.java)
            startActivity(intent)
            true
        }
    }
}
