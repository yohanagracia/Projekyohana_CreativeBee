package com.example.projekmobileyohana

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.projekmobileyohana.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString()

            val savedUsername = sharedPref.getString("username", null)
            val savedPassword = sharedPref.getString("password", null)
            val isRegistered = sharedPref.getBoolean("isRegistered", false)

            // Login Admin
            val validAdmin =
                username == "adminbee" &&
                        password == "beeadmin123"

            // Login Customer yang sudah terdaftar
            val validRegisteredLogin =
                isRegistered &&
                        username == savedUsername &&
                        password == savedPassword

            // Login praktikum yang sudah ada
            val validPracticumLogin =
                username.isNotEmpty() &&
                        password.isNotEmpty() &&
                        username == password

            when {

                validAdmin -> {
                    sharedPref.edit()
                        .putBoolean("isLogin", true)
                        .putString("userRole", "admin")
                        .apply()

                    startActivity(
                        Intent(this, CalendarWorkshopActivity::class.java)
                    )
                    finish()
                }

                validRegisteredLogin || validPracticumLogin -> {
                    sharedPref.edit()
                        .putBoolean("isLogin", true)
                        .putString("userRole", "customer")
                        .apply()

                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                    finish()
                }

                else -> {
                    AlertDialog.Builder(this)
                        .setTitle("Login Gagal")
                        .setMessage("Username atau password salah. Silakan coba lagi.")
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
        }

        binding.btnRegisterPage.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
