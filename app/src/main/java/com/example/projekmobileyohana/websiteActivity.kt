package com.example.projekmobileyohana

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.projekmobileyohana.databinding.ActivityWebsiteBinding

class WebsiteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebsiteBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebsiteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        val toolbar = binding.toolbarWebsite
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // WebView
        val webView = binding.webView

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.loadsImagesAutomatically = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true

        // Tes tampilan WebView
        webView.loadData(
            """
            <html>
                <body>
                    <h1>Creative Bee</h1>
                    <h2>Website Creative Bee</h2>
                    <p>WebView berhasil ditampilkan di dalam aplikasi.</p>
                    <p>Creative Bee - Clay Workshop</p>
                </body>
            </html>
            """.trimIndent(),
            "text/html",
            "UTF-8"
        )

        // Tombol Back Android
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                }
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
