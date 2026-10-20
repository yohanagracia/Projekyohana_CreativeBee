package com.example.projekmobileyohana

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projekmobileyohana.databinding.ActivityWorkshopBinding

class WorkshopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkshopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        val toolbar = binding.toolbarWorkshop
        setSupportActionBar(toolbar)

        // Tombol Back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Tombol Pesan Workshop
        val btnBookWorkshop = binding.btnBookWorkshop

        btnBookWorkshop.setOnClickListener {
            val url = "https://www.google.com/search?q=Creative+Bee+clay+workshop"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        // Tombol Lihat Website Creative Bee
        val btnWorkshopWebsite = binding.btnWorkshopWebsite

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
