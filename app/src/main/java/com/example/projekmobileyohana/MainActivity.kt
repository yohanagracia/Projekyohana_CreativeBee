
package com.example.projekmobileyohana

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Mengatur jarak dari system bars
        val mainView = findViewById<android.view.View>(R.id.main)

        ViewCompat.setOnApplyWindowInsetsListener(mainView) { view, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Tombol menuju halaman Katalog
        findViewById<Button>(R.id.btnCatalog).setOnClickListener {
            startActivity(
                Intent(this, CatalogActivity::class.java)
            )
        }

        // Tombol menuju halaman Workshop
        findViewById<Button>(R.id.btnWorkshop).setOnClickListener {
            startActivity(
                Intent(this, WorkshopActivity::class.java)
            )
        }

        // Tombol menuju Profil Developer
        findViewById<Button>(R.id.btnProfileDeveloper).setOnClickListener {
            startActivity(
                Intent(this, ProfileDeveloperActivity::class.java)
            )
        }

        // Tombol Logout
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah kamu yakin ingin keluar?")
                .setNegativeButton("Tidak", null)
                .setPositiveButton("Ya") { _, _ ->
                    val sharedPref = getSharedPreferences(
                        "user_pref",
                        MODE_PRIVATE
                    )

                    sharedPref.edit().clear().apply()

                    val intent = Intent(this, AuthActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                    finish()
                }
                .show()
        }
    }
}
