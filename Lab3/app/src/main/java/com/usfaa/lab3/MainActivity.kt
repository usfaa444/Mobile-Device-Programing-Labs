package com.usfaa.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.EditText
import android.widget.Space
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.marginStart

class MainActivity : AppCompatActivity() {

    private lateinit var txtAndroidVersion: EditText
    private lateinit var txtAndroidCodeName: EditText
    private lateinit var btnAdd: Button
    private lateinit var tableLayout: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtAndroidCodeName = findViewById(R.id.lbl_android_code_name)
        txtAndroidVersion = findViewById(R.id.lbl_android_version)
        btnAdd = findViewById(R.id.btn_add)
        tableLayout = findViewById(R.id.table_layout)

        btnAdd.setOnClickListener {
            checkForValidInput()
        }
    }

    private fun checkForValidInput() {
        val codeName = txtAndroidCodeName.text.toString()
        val version = txtAndroidVersion.text.toString()
        if (codeName.isEmpty()) {
            txtAndroidCodeName.error = "Please fill the Android Code Name"
            return
        }
        if (version.isEmpty()) {
            txtAndroidVersion.error = "Please fill the Android Version"
            return
        }
        addRowToTableLayout(codeName, version)
    }

    private fun addRowToTableLayout(codeName: String, version: String) {
        val tableRow = TableRow(applicationContext)
        val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT)
        tableRow.layoutParams = layoutParams
        val lblCodeName = TextView(applicationContext)
        lblCodeName.setBackgroundResource(R.color.pink)
        lblCodeName.text = codeName
        tableRow.addView(lblCodeName,0)

        val lblVersion = TextView(applicationContext)
        lblVersion.setBackgroundResource(R.color.pink)
        lblVersion.text = version
        layoutParams.setMargins(450,0,0,0)
        lblVersion.layoutParams = layoutParams
        tableRow.addView(lblVersion,1)

        tableLayout.addView(tableRow)
    }
}