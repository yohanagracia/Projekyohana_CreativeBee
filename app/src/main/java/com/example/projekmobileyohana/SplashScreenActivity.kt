package com.example.projekmobileyohana

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.projekmobileyohana.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            // Menampilkan splash selama 2 detik
            delay(2000)

            // Membaca status login yang tersimpan
            val sharedPref = getSharedPreferences(
                "user_pref",
                MODE_PRIVATE
            )

            val isLogin = sharedPref.getBoolean(
                "isLogin",
                false
            )

            // Menentukan halaman tujuan
            val tujuan = if (isLogin) {
                MainActivity::class.java
            } else {
                AuthActivity::class.java
            }

            startActivity(Intent(this@SplashScreenActivity, tujuan))
            finish()
        }
    }
}
