package com.example.opiniaodetudo.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.pages.fragments.ListFragment

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_review_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_list,
                ListFragment()
            )
            .commit()
    }


}
