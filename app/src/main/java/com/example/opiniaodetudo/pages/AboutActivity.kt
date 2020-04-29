package com.example.opiniaodetudo.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.pages.fragments.SettingsFragment


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseTheme()
        setContentView(R.layout.about_activity)
    }

    private fun chooseTheme() {
        val nightMode = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(SettingsFragment.NIGHT_MODE_PREF, false)
        if (nightMode) {
            setTheme(R.style.AppThemeNight_NoActionBar)
        } else {
            setTheme(R.style.AppTheme_NoActionBar)
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}

