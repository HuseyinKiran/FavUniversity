package com.huseyinkiran.favuniversities.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.huseyinkiran.favuniversities.databinding.FragmentWebsiteBinding


class WebsiteFragment : Fragment() {

    private var _binding: FragmentWebsiteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebsiteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val websiteUrl = arguments?.getString("WEBSITE_URL") ?: ""
        val universityName = arguments?.getString("UNIVERSITY_NAME") ?: ""
        setupWebView(websiteUrl)

        binding.goBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.txtToolbar.text = universityName

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String) {
        with(binding.webView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }
        binding.webView.webViewClient = WebViewClient()
        if (url.contains("https://")) {
            binding.webView.loadUrl(url)
        } else {
            binding.webView.loadUrl("https://$url")
        }
    }

}