package com.example.projekmobileyohana

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projekmobileyohana.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Periksa status login pengguna
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val isLogin = sharedPref.getBoolean("isLogin", false)

        // Jika belum login, arahkan ke halaman autentikasi
        if (!isLogin) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        // Tampilkan halaman utama
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Atur navigasi bawah
        binding.bottomNavigation.setOnItemSelectedListener { item ->

            val selectedFragment: Fragment? = when (item.itemId) {

                R.id.menu_home -> HomeFragment()

                R.id.menu_catalog -> CatalogFragment()

                // Menu Workshop membuka halaman Workshop lama
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

        // Tampilkan halaman Home saat aplikasi pertama kali dibuka
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.menu_home
        }
    }

    // Fungsi untuk menampilkan fragment
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}