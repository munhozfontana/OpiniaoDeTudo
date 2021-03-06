package com.example.opiniaodetudo.pages

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.pages.fragments.FormFragment
import com.example.opiniaodetudo.pages.fragments.ListFragment
import com.example.opiniaodetudo.pages.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val fragments = mapOf(
        FORM_FRAGMENT to ::FormFragment,
        LIST_FRAGMENT to ::ListFragment,
        SETTINGS_FRAGMENT to ::SettingsFragment
    )

    companion object {
        const val FORM_FRAGMENT = R.id.menuitem_newitem
        const val LIST_FRAGMENT = R.id.menuitem_listitem
        const val SETTINGS_FRAGMENT = R.id.menuitem_settings
        const val GPS_PERMISSION_REQUEST = 1231
        const val PUSH_NOTIFICATION_MESSAGE_REQUEST = 1232
        const val PUSH_NOTIFICATION_CHANNEL = "PushNotificationChannel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseTheme()
        setContentView(R.layout.activity_main)

        navigateTo(FORM_FRAGMENT)

        configureAutoHiddenKeyboard()
        configureBottomMenu()
        askForGPSPermission()

    }

    private fun configureAutoHiddenKeyboard() {
        val mainContainer = findViewById<ConstraintLayout>(R.id.main_container)
        mainContainer.setOnTouchListener { _, _ ->
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            Log.d("check_null", ("" + imm))
            Log.d("check_null", ("" + currentFocus))
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    public fun navigateTo(item: Int) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        val fragmentInstance: Fragment = fragments[item]?.invoke()!!
        supportFragmentManager.beginTransaction().setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        ).replace(R.id.fragment_container_main, fragmentInstance).commit()

    }

    private fun configureBottomMenu() {
        val bottomNavigationMenu =
            findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationMenu.setOnNavigationItemSelectedListener {
            navigateTo(it.itemId)
            true
        }
    }


    private fun askForGPSPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                MainActivity.GPS_PERMISSION_REQUEST
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            GPS_PERMISSION_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "Permissão para usar o GPS concedida", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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

    fun setNightMode() {
        recreate()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    fun navigateWithBackStack(destiny: Fragment) {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
            .replace(R.id.fragment_container_main, destiny)
            .addToBackStack(null)
            .commit()
    }

    fun sendNotification(title: String, message: String) {
        val messageId = System.currentTimeMillis().toInt()
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                this,
                PUSH_NOTIFICATION_MESSAGE_REQUEST,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(
            this, PUSH_NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .build()
        NotificationManagerCompat.from(this).notify(messageId, notification)
    }


}
