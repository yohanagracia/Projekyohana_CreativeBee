package com.example.projekmobileyohana

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OnboardingActivity : AppCompatActivity() {

    private var currentPage = 0

    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvIndicator: TextView
    private lateinit var btnNext: Button
    private lateinit var btnSkip: TextView

    private val titles = arrayOf(
        "Welcome to Creative Bee",
        "Explore Our Collection",
        "Join Our Workshop"
    )

    private val descriptions = arrayOf(
        "Temukan dunia kreativitas melalui kerajinan clay handmade.",
        "Lihat berbagai koleksi produk clay lucu dan unik dari Creative Bee.",
        "Ikuti workshop clay dan kembangkan kreativitas bersama Creative Bee!"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        tvTitle = findViewById(R.id.tvOnboardingTitle)
        tvDescription = findViewById(R.id.tvOnboardingDescription)
        tvIndicator = findViewById(R.id.tvIndicator)
        btnNext = findViewById(R.id.btnNext)
        btnSkip = findViewById(R.id.btnSkip)

        showPage()

        btnNext.setOnClickListener {
            if (currentPage < titles.size - 1) {
                currentPage++
                showPage()
            } else {
                finishOnboarding()
            }
        }

        btnSkip.setOnClickListener {
            finishOnboarding()
        }
    }

    private fun showPage() {
        tvTitle.text = titles[currentPage]
        tvDescription.text = descriptions[currentPage]

        tvIndicator.text = when (currentPage) {
            0 -> "● ○ ○"
            1 -> "○ ● ○"
            else -> "○ ○ ●"
        }

        btnNext.text =
            if (currentPage == titles.size - 1) "Mulai Sekarang" else "Lanjut"
    }

    private fun finishOnboarding() {
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        sharedPref.edit()
            .putBoolean("onboardingCompleted", true)
            .apply()

        val isLogin = sharedPref.getBoolean("isLogin", false)

        val destination = if (isLogin) {
            MainActivity::class.java
        } else {
            AuthActivity::class.java
        }

        startActivity(Intent(this, destination))
        finish()
    }
}
