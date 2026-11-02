package com.example.projekmobileyohana

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CalendarWorkshopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(
                android.R.id.content,
                CalendarWorkshopFragment()
            )
            .commit()
    }
}