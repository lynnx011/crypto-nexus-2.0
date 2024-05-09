package com.example.cryptocurrency.presentation.screens
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentNewsDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailsBinding
    private lateinit var newsUrl: String
    private lateinit var webView: WebView
    private lateinit var webViewClient: WebViewClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsUrl = it.getString("newsUrl").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = binding.webView
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY

        webSettings.useWideViewPort = true
        webViewClient = WebViewClient()
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view!!.loadUrl(newsUrl)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.circular.isVisible = false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.circular.isVisible = false
            }
        }
        webView.loadUrl(newsUrl)

    }
}