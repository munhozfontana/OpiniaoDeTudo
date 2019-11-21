package com.example.opiniaodetudo.pages

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.pages.fragments.FormFragment
import com.example.opiniaodetudo.pages.fragments.ListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {

    private val fragments = mapOf(
        R.id.menuitem_newitem to ::FormFragment,
        R.id.menuitem_listitem to ::ListFragment
    )

    companion object {
        const val GPS_PERMISSION_REQUEST = 1231
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureAutoHiddenKeyboard()
        navigateTo(R.id.menuitem_newitem)
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


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun navigateTo(item: Int) {
        val fragmentInstance: Fragment = fragments[item]?.invoke()!!
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_main, fragmentInstance)
            .addToBackStack(item.toString()).commit()
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



}
