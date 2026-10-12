
package com.example.projekmobileyohana

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ProfileDeveloperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_developer)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(
            R.id.toolbarProfile
        )

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
