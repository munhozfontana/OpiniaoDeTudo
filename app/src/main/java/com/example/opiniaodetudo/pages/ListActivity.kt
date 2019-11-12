package com.example.opiniaodetudo.pages

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.infra.repositories.ReviewRepository
import com.example.opiniaodetudo.domain.Review

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_review_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_list, ListFragment())
            .commit()
    }


}
