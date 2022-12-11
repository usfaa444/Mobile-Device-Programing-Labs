package com.usfaa.cvbuilder.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.usfaa.cvbuilder.databinding.FragmentWebViewBinding
import com.usfaa.cvbuilder.ui.base.BaseFragment

class WebViewFragment : BaseFragment() {
    private lateinit var binding: FragmentWebViewBinding
    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webview.run {
            settings.javaScriptEnabled = true
            loadUrl(args.url)
        }
    }

    override fun shouldShowBottomNavigation() = false
}