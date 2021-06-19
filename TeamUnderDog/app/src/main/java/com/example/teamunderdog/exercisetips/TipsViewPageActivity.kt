package com.example.exercisetips

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

import com.example.teamunderdog.databinding.ExerciseTipPageBinding

class TipsViewPageActivity : AppCompatActivity(){

    lateinit var binding: ExerciseTipPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ExerciseTipPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val intent = getIntent()
        val url = intent.getStringExtra("url")

        binding.apply {
            webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled=true
            webView.settings.builtInZoomControls = true
            webView.settings.defaultTextEncodingName = "utf-8"
            webView.loadUrl(url.toString())
        }
    }
}