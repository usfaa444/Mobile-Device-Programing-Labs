package com.usfaa.quizzapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CatalogActivity : AppCompatActivity() {

    private var userName: String? = ""
    private lateinit var lblUserName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        userName = intent.extras?.getString(USERNAME_KEY, DEFAULT_USERNAME)
        lblUserName = findViewById(R.id.lblUserName)
        lblUserName.text = "Welcome $userName"
    }

    companion object {
        const val USERNAME_KEY = "userName"
        const val DEFAULT_USERNAME = "username@gmail.com"
    }
}