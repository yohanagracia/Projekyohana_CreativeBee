package com.example.projekmobileyohana

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projekmobileyohana.databinding.ActivityCatalogBinding

class CatalogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbarCatalog)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Data produk
        val products = listOf(
            Product(
                "Clay Honey Bear",
                "BEST SELLER",
                R.drawable.clay_honey_bear,
                "⭐ 4.9 (128)",
                "Rp35.000"
            ),
            Product(
                "Clay Froggie Pot",
                "NEW",
                R.drawable.clay_froggie_pot,
                "⭐ 4.8 (96)",
                "Rp38.000"
            ),
            Product(
                "White Rabbit Clay",
                "BEST SELLER",
                R.drawable.white_rabbit_clay,
                "⭐ 4.7 (82)",
                "Rp32.000"
            ),
            Product(
                "Mini Croissant Clay",
                "NEW",
                R.drawable.mini_croissant_clay,
                "⭐ 4.9 (110)",
                "Rp28.000"
            )
        )

        // RecyclerView dengan tampilan Grid 2 kolom
        binding.rvCatalog.layoutManager = GridLayoutManager(this, 2)
        binding.rvCatalog.adapter = CatalogAdapter(products)

        // Tombol Website
        binding.btnWebsite.setOnClickListener {
            val intent = Intent(this, WebsiteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}