package com.example.projekmobileyohana

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class WorkshopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workshop)

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarWorkshop)
        setSupportActionBar(toolbar)

        // Tombol Back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Tombol Pesan Workshop
        val btnBookWorkshop = findViewById<Button>(R.id.btnBookWorkshop)

        btnBookWorkshop.setOnClickListener {
            val url = "https://www.google.com/search?q=Creative+Bee+clay+workshop"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        // Tombol Lihat Website Creative Bee
        val btnWorkshopWebsite = findViewById<Button>(R.id.btnWorkshopWebsite)

        btnWorkshopWebsite.setOnClickListener {
            val intent = Intent(this, WebsiteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}