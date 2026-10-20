package com.example.projekmobileyohana

import android.content.Intent
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import androidx.appcompat.app.AppCompatActivity
import com.example.projekmobileyohana.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name").orEmpty()
        val phone = intent.getStringExtra("phone").orEmpty()
        val username = intent.getStringExtra("username").orEmpty()
        val password = intent.getStringExtra("password").orEmpty()

        binding.btnVerify.setOnClickListener {
            val otp = binding.etOtp.text.toString().trim()

            if (otp.isEmpty() || otp != phone) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Verifikasi Gagal")
                    .setMessage(
                        "Kode OTP salah atau kosong. Masukkan nomor handphone saat registrasi."
                    )
                    .setPositiveButton("OK", null)
                    .show()

                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

            sharedPref.edit()
                .putString("name", name)
                .putString("phone", phone)
                .putString("username", username)
                .putString("password", password)
                .putBoolean("isRegistered", true)
                .putBoolean("isLogin", false)
                .apply()

            MaterialAlertDialogBuilder(this)
                .setTitle("Registrasi Berhasil")
                .setMessage("Akun berhasil disimpan. Silakan login.")
                .setPositiveButton("OK") { _, _ ->
                    val intent = Intent(this, AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    finish()
                }
                .show()
        }
    }
}
