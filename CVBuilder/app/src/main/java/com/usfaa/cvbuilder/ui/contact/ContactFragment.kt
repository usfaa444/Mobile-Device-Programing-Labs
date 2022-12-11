package com.usfaa.cvbuilder.ui.contact

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.usfaa.cvbuilder.R
import com.usfaa.cvbuilder.databinding.FragmentContactBinding
import com.usfaa.cvbuilder.ui.base.BaseFragment

class ContactFragment : BaseFragment() {

    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.run {
            facebook.setOnClickListener { showUrl("https://www.facebook.com/") }
            twitter.setOnClickListener { showUrl("https://twitter.com/") }
            instagram.setOnClickListener { showUrl("https://instagram.com/") }
            linkedin.setOnClickListener { showUrl("https://www.linkedin.com/") }
            phone.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+12223334444"))
                startActivity(intent)
            }
            github.setOnClickListener { showUrl("https://github.com/") }
        }
    }

    private fun showUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun shouldShowBottomNavigation() = true

}