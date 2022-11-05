package com.usfaa.walmartlogingpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.usfaa.walmartlogingpage.data.models.User

class MainActivity : AppCompatActivity() {

    val users = mutableListOf(User("Faycal", "Maitchibi", "usfaa444@gmail.com", "usfaa444"),
        User("Sohahib", "Yaoda", "yaoda@gmail.com", "yaoda"),
        User("Nassirou", "Smanadoulogou", "nassirou@gmail.com", "nassirou"),
        User("John", "Doe", "john@gmail.com", "john"),
        User("Alice", "Myers", "alice@gmail.com", "alice"))

    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var btnCreateAccount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        btnSignIn = findViewById(R.id.btnSignin)
        btnCreateAccount = findViewById(R.id.btnCreateAccount)

        btnSignIn.setOnClickListener { validateInputs() }
    }

    private fun validateInputs() {
        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()
        if (email.isEmpty()) {
            txtEmail.error = "Please fill in your email"
            return
        }
        if (password.isEmpty()) {
            txtPassword.error = "Please fill in your password"
            return
        }
        signIn(email, password)
    }

    private fun signIn(email: String, password: String) {
        var found = false
        for (user in users) {
            if (user.userName == email && user.password == password) {
                goToCatalogPage(user.userName)
                found = true
                break
            }
        }
        if (!found) {
            showMessage("No user found with the credentials you entered")
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun goToCatalogPage(userName: String) {
        val intent = Intent(this, CatalogActivity::class.java)
        intent.putExtra(CatalogActivity.USERNAME_KEY, userName)
        startActivity(intent)

    }
}