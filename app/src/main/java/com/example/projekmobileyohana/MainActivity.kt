package com.example.projekmobileyohana

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projekmobileyohana.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigation: com.google.android.material.bottomnavigation.BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menggunakan View Binding untuk layout utama aplikasi
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menghubungkan Bottom Navigation menggunakan View Binding
        bottomNavigation = binding.bottomNavigation

        // Mengatur perpindahan halaman berdasarkan menu
        bottomNavigation.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.menu_home -> HomeFragment()
                R.id.menu_catalog -> CatalogFragment()
                R.id.menu_workshop -> WorkshopFragment()
                R.id.menu_profile -> ProfileFragment()
                else -> null
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment)
                true
            } else {
                false
            }
        }

        // Menampilkan Beranda saat aplikasi pertama dibuka
        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.menu_home
        }
    }

    // Menampilkan Fragment yang dipilih
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
