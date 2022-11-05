package com.usfaa.walmartlogingpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView

class CatalogActivity : AppCompatActivity() {
    private var userName: String? = ""
    private lateinit var lblUserName: TextView
    private lateinit var electronicCardView : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        userName = intent.extras?.getString(USERNAME_KEY, DEFAULT_USERNAME)
        lblUserName = findViewById(R.id.lblUserName)
        electronicCardView = findViewById(R.id.electronic_card_view)
        lblUserName.text = "Welcome $userName"

        electronicCardView.setOnClickListener {
            startActivity(Intent(this, ProductPage::class.java))
        }
    }

    companion object {
        const val USERNAME_KEY = "userName"
        const val DEFAULT_USERNAME = "username@gmail.com"
    }
}