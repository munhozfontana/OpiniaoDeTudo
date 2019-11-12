package com.example.opiniaodetudo.pages

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.domain.Review
import com.example.opiniaodetudo.infra.repositories.ReviewRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_main, FormFragment())
            .commit()
        configureAutoHiddenKeyboard()
    }

    private fun configureAutoHiddenKeyboard(){
        val mainContainer = findViewById<ConstraintLayout>(R.id.main_container)
        mainContainer.setOnTouchListener { _ , _ ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}
