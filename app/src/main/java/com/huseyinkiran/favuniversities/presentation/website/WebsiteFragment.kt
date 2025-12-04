package com.huseyinkiran.favuniversities.presentation.website

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.core.ui.viewBinding
import com.huseyinkiran.favuniversities.databinding.FragmentWebsiteBinding
import kotlinx.coroutines.launch

class WebsiteFragment : Fragment(R.layout.fragment_website) {

    private val binding by viewBinding(FragmentWebsiteBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        lifecycleScope.launch {
            requireActivity().findViewById<LinearLayout>(R.id.customBottomBar).isGone = true
        }

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
    private fun setupWebView(url: String) = with(binding.webView) {
        with(settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }

        webViewClient = WebViewClient()
        if (url.contains("https://")) {
            loadUrl(url)
        } else {
            loadUrl("https://$url")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<LinearLayout>(R.id.customBottomBar).visibility =
            View.VISIBLE
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

}