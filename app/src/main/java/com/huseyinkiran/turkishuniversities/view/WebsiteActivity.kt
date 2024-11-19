package com.huseyinkiran.turkishuniversities.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.huseyinkiran.turkishuniversities.databinding.ActivityWebsiteBinding

class WebsiteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebsiteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebsiteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val websiteUrl = intent.getStringExtra("WEBSITE_URL") ?: ""
        val uniName = intent.getStringExtra("UNIVERSITY_NAME") ?: ""

        setupWebView()

        val validUrl = formatUrl(websiteUrl)

        binding.webView.loadUrl(validUrl)

        binding.goBack.setOnClickListener {
            finish()
        }

        binding.txtToolbar.text = uniName
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        with(binding.webView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)

            }
        }
    }

    private fun formatUrl(url: String): String {
        return if (url.startsWith("http://") || url.startsWith("https://")) {
            url
        } else {
            "https://$url"
        }
    }
}
