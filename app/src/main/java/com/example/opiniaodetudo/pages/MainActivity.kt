package com.example.opiniaodetudo.pages

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.pages.fragments.FormFragment
import com.example.opiniaodetudo.pages.fragments.ListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {

    private val fragments = mapOf(FORM_FRAGMENT to ::FormFragment, LIST_FRAGMENT to ::ListFragment)

    companion object {
        val FORM_FRAGMENT = "formFragment"
        val LIST_FRAGMENT = "listFragment"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureAutoHiddenKeyboard()
        navigateTo(FORM_FRAGMENT)
        configureBottomMenu()
    }


    private fun configureAutoHiddenKeyboard() {
        val mainContainer = findViewById<ConstraintLayout>(R.id.main_container)
        mainContainer.setOnTouchListener { _, _ ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun navigateTo(item: String) {
        val fragmentInstance: Fragment = fragments[item]?.invoke()!!
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_main, fragmentInstance)
            .addToBackStack(item).commit()
    }

    private fun configureBottomMenu() {
        val bottomNavigationMenu = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuitem_newitem -> navigateTo(it.itemId.toString())
            }
            true
        }
    }


}
