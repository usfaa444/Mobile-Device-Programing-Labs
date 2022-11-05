package com.usfaa.walmartlogingpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.usfaa.walmartlogingpage.data.models.Product

class ProductDetailsPage : AppCompatActivity() {
    private lateinit var imgProduct: ImageView
    private lateinit var lblName: TextView
    private lateinit var lblColor: TextView
    private lateinit var lblId: TextView
    private lateinit var lblDescription: TextView
    private var product : Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details_page)
        imgProduct = findViewById(R.id.img_product)
        lblName = findViewById(R.id.lbl_name)
        lblId = findViewById(R.id.lbl_item_id)
        lblColor = findViewById(R.id.lbl_color)
        lblDescription = findViewById(R.id.lbl_description)

        product = intent.extras?.get(ProductPage.PRODUCT_KEY) as Product?
        showProductInfo()
    }

    private fun showProductInfo() {
        product?.let {
            imgProduct.setImageResource(it.image)
            lblName.text = it.title
            lblColor.text = "Color : ${it.color}"
            lblId.text = "Walmart #: ${it.itemId}"
            lblDescription.text = "Item Description:\n${it.description}"
        } ?: kotlin.run {
            Toast.makeText(this, "No Product Found", Toast.LENGTH_LONG).show()
        }
    }
}