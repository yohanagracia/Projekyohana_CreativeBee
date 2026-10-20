
package com.example.projekmobileyohana

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projekmobileyohana.databinding.ActivityProfileDeveloperBinding

class ProfileDeveloperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDeveloperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbarProfile

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
