package com.example.projekmobileyohana

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class CatalogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarCatalog)
        setSupportActionBar(toolbar)

        // Tombol Back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Tombol Website
        val btnWebsite = findViewById<Button>(R.id.btnWebsite)

        btnWebsite.setOnClickListener {
            val intent = Intent(this, WebsiteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}