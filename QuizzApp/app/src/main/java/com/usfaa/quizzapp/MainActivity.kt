package com.usfaa.quizzapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var buttonSubmit: Button
    private lateinit var buttonReset: Button
    private lateinit var rb1: RadioButton
    private lateinit var rb2: RadioButton
    private lateinit var rb3: RadioButton
    private lateinit var cb1: CheckBox
    private lateinit var cb2: CheckBox
    private lateinit var cb3: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSubmit = findViewById(R.id.button_submit)
        buttonReset = findViewById(R.id.button_reset)
        rb1 = findViewById(R.id.rb1)
        rb2 = findViewById(R.id.rb2)
        rb3 = findViewById(R.id.rb3)
        cb1 = findViewById(R.id.cb1)
        cb2 = findViewById(R.id.cb2)
        cb3 = findViewById(R.id.cb3)

        buttonSubmit.setOnClickListener { checkAnswers() }
        buttonReset.setOnClickListener { resetSelection() }
    }

    private fun checkAnswers() {
        var result = 0
        if (rb2.isChecked) result += 50
        if (cb1.isChecked && !cb2.isChecked && cb3.isChecked) result += 50
        val dialog = AlertDialog.Builder(this).create()
        dialog.setTitle("Quizzes Results")
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:SS")
        val dateTime = sdf.format(Date())
        val resultMessage = "You submitted your answers on current date: $dateTime\nYour score is $result%"
        dialog.setMessage(resultMessage)
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Got it!") { _, _ -> dialog.dismiss() }
        dialog.show()
    }

    private fun resetSelection() {
        rb1.isChecked = false
        rb2.isChecked = false
        rb3.isChecked = false
        cb1.isChecked = false
        cb2.isChecked = false
        cb3.isChecked = false
    }
}