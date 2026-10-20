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

        // Periksa status login
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val isLogin = sharedPref.getBoolean("isLogin", false)

        if (!isLogin) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        // Tampilkan halaman utama jika sudah login
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = binding.bottomNavigation

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

        // Tampilkan Beranda saat pertama dibuka
        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.menu_home
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
