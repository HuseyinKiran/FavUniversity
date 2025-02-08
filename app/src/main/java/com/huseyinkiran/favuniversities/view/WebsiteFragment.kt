package com.huseyinkiran.favuniversities.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.huseyinkiran.favuniversities.databinding.FragmentWebsiteBinding


class WebsiteFragment : Fragment() {

    private var _binding: FragmentWebsiteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebsiteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle: WebsiteFragmentArgs by navArgs()
        val websiteUrl = bundle.WEBSITEURL
        val universityName = bundle.UNIVERSITYNAME

        setupWebView(websiteUrl)
        binding.txtToolbar.text = universityName

        binding.goBack.setOnClickListener {
            findNavController().popBackStack()
        }

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